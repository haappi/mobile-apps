package io.github.haappi;

import com.google.gson.annotations.SerializedName;

public class Question {
    @SerializedName("id")
    private int id;

    @SerializedName("image_type")
    private String imageType;

    @SerializedName("image_data")
    private String imageData;

    @SerializedName("question")
    private String question;

    @SerializedName("answers")
    private String[] answers;

    @SerializedName("correct")
    private int correct;

    @SerializedName("explanation")
    private String explanation;

    @SerializedName("hint")
    private String hint;

    @SerializedName("difficulty")
    private int difficulty;

    @SerializedName("category")
    private String category;

    @SerializedName("tags")
    private String[] tags;

    @SerializedName("source")
    private String source;

    @SerializedName("source_url")
    private String sourceUrl;

    public int getId() {
        return id;
    }

    public String getImageType() {
        return imageType;
    }

    public String getImageData() {
        return imageData;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrect() {
        return correct;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getHint() {
        return hint;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getCategory() {
        return category;
    }

    public String[] getTags() {
        return tags;
    }

    public String getSource() {
        return source;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }
}
