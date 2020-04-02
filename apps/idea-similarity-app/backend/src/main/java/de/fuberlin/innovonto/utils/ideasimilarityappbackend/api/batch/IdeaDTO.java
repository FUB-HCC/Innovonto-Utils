package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.batch;

public class IdeaDTO {
    private String id;
    private String content;

    public IdeaDTO(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
