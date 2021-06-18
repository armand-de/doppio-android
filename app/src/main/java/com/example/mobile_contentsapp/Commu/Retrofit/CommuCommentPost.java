package com.example.mobile_contentsapp.Commu.Retrofit;

public class CommuCommentPost {
    private int postId;
    private String contents;

    public CommuCommentPost(int postId, String contents) {
        this.postId = postId;
        this.contents = contents;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}

