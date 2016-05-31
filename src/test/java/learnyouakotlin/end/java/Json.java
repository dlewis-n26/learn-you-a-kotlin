package learnyouakotlin.end.java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Json {
    private static final JsonNodeFactory nodes = JsonNodeFactory.instance;
    private static final ObjectMapper stableMapper = new ObjectMapper().enable(INDENT_OUTPUT, ORDER_MAP_ENTRIES_BY_KEYS);

    public static Map.Entry<String, JsonNode> prop(String name, String textValue) {
        return prop(name, nodes.textNode(textValue));
    }

    public static Map.Entry<String, JsonNode> prop(String name, JsonNode value) {
        return new Map.Entry<String, JsonNode>() {
            @Override
            public String getKey() {
                return name;
            }

            @Override
            public JsonNode getValue() {
                return value;
            }

            @Override
            public JsonNode setValue(JsonNode value) {
                throw new UnsupportedOperationException("unmodifiable");
            }
        };
    }

    public static ObjectNode obj(Iterable<Map.Entry<String, JsonNode>> props) {
        ObjectNode object = nodes.objectNode();
        props.forEach(p -> {
            // p can be null, but no way to annotate the Map.Entry within the Iterable
            if (p != null) {
                object.set(p.getKey(), p.getValue());
            }
        });
        return object;
    }

    @SafeVarargs
    public static ObjectNode obj(Map.Entry<String, JsonNode>... props) {
        // Elements of props can be null, but no way to annotate the parameter with @Nullable to indicate that
        return obj(asList(props));
    }

    public static ArrayNode array(Iterable<JsonNode> elements) {
        ArrayNode array = nodes.arrayNode();
        elements.forEach(array::add);
        return array;
    }

    public static <T> ArrayNode array(List<T> elements, Function<T, JsonNode> fn) {
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
