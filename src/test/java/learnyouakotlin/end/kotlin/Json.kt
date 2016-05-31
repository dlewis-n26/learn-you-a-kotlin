package learnyouakotlin.end.kotlin

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import java.util.Arrays.asList

private val nodes = JsonNodeFactory.instance
private val stableMapper = ObjectMapper().enable(INDENT_OUTPUT, ORDER_MAP_ENTRIES_BY_KEYS)

infix fun String.of(textValue: String): Pair<String, JsonNode> {
    return of(nodes.textNode(textValue))
}

infix fun String.of(value: JsonNode) = this to value

fun obj(props: Iterable<Pair<String, JsonNode>>) = nodes.objectNode().apply {
    props.forEach { p -> set(p.first, p.second) }
}

fun obj(vararg props: Pair<String, JsonNode>): ObjectNode {
    return obj(asList<Pair<String, JsonNode>>(*props))
}

fun array(elements: Iterable<JsonNode>) = nodes.arrayNode().apply { elements.forEach { add(it) } }
fun <T> array(elements: List<T>, fn: (T) -> JsonNode) = array(elements.map(fn))

fun JsonNode.asStableJsonString(): String {
    try {
        return stableMapper.writeValueAsString(this)
    }
    catch (e: JsonProcessingException) {
        throw IllegalArgumentException("failed to convert JsonNode to JSON string", e)
    }
}


