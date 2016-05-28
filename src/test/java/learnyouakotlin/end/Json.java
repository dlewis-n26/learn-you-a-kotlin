package learnyouakotlin.end;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

public class Json {
    private static final JsonNodeFactory nodes = JsonNodeFactory.instance;
    private static final ObjectMapper stableMapper = new ObjectMapper().enable(INDENT_OUTPUT, ORDER_MAP_ENTRIES_BY_KEYS);

    public static Map.Entry<String, JsonNode> prop(String name, String textValue) {
        return prop(name, nodes.textNode(textValue));
    }

    public static Map.Entry<String, JsonNode> prop(String name, int intValue) {
        return prop(name, nodes.numberNode(intValue));
    }

    public static Map.Entry<String, JsonNode> prop(String name, @Nullable Integer intValue) {
        return prop(name, nodes.numberNode(intValue));
    }

    public static Map.Entry<String, JsonNode> prop(String name, boolean boolValue) {
        return prop(name, nodes.booleanNode(boolValue));
    }

    public static Map.Entry<String, JsonNode> prop(String name, JsonNode value) {
        return Collections.singletonMap(name, value).entrySet().iterator().next();
    }

    public static ObjectNode object(Iterable<Map.Entry<String, JsonNode>> props) {
        ObjectNode object = nodes.objectNode();
        props.forEach(p -> object.set(p.getKey(), p.getValue()));
        return object;
    }

    @SafeVarargs
    public static ObjectNode object(Map.Entry<String, JsonNode>... props) {
        return object(asList(props));
    }

    public static ArrayNode array(Iterable<JsonNode> elements) {
        ArrayNode array = nodes.arrayNode();
        elements.forEach(array::add);
        return array;
    }

    public static ArrayNode array(JsonNode singleElement) {
        return array(singleton(singleElement));
    }

    public static ArrayNode array(JsonNode... elements) {
        return array(asList(elements));
    }

    public static <T> ArrayNode mapToJsonArray(List<T> elements, Function<T, JsonNode> fn) {
        return array(elements.stream().map(fn).collect(toList()));
    }

    public static String asStableJsonString(JsonNode n) {
        try {
            return stableMapper.writeValueAsString(n);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("failed to convert JsonNode to JSON string", e);
        }
    }
}
