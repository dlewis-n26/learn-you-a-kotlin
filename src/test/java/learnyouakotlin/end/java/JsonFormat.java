package learnyouakotlin.end.java;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliterator;
import static java.util.stream.StreamSupport.stream;
import static learnyouakotlin.end.java.Json.mapToJsonArray;
import static learnyouakotlin.end.java.Json.object;
import static learnyouakotlin.end.java.Json.prop;

public class JsonFormat {
    public static JsonNode sessionAsJson(Session session) {
        return object(
                prop("code", session.code.toString()),
                prop("title", session.title),
                prop("presenters", mapToJsonArray(session.presenters, JsonFormat::presenterAsJson)));
    }

    public static Session sessionFromJson(JsonNode json) throws JsonMappingException {
        try {
            SessionCode code = SessionCode.parse(json.path("code").asText());
            String title = json.path("title").asText();

            JsonNode authorsNode = json.path("presenters");
            List<Presenter> authors = stream(spliterator(authorsNode.elements(), authorsNode.size(), ORDERED), false)
                    .map(JsonFormat::presenterFromJson)
                    .collect(Collectors.toList());

            return new Session(code, title, authors);

        } catch (ParseException e) {
            throw new JsonMappingException(null, "failed to parse Book from JSON", e);
        }
    }

    private static ObjectNode presenterAsJson(Presenter p) {
        return object(prop("name", p.name));
    }

    private static Presenter presenterFromJson(JsonNode authorNode) {
        return new Presenter(authorNode.path("name").asText());
    }
}
