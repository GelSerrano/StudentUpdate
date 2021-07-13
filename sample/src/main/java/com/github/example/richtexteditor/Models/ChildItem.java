package com.github.example.richtexteditor.Models;

public class ChildItem {
    // Declaration of the variable
    private String ChildItemTitle;
    private String ChildItemsubtitle;
    private String content;

    // Constructor of the class
    // to initialize the variable*
    public ChildItem(String childItemTitle, String childItemsubtitle, String content)
    {
        this.ChildItemTitle = childItemTitle;
        this.ChildItemsubtitle = childItemsubtitle;
        this.content = content;
    }

    // Getter and Setter method
    // for the parameter
    public String getChildItemTitle()
    {
        return ChildItemTitle;
    }

    public void setChildItemTitle(
            String childItemTitle)
    {
        ChildItemTitle = childItemTitle;
    }

    public String getChildItemsubtitle() {
        return ChildItemsubtitle;
    }

    public void setChildItemsubtitle(String childItemsubtitle) {
        ChildItemsubtitle = childItemsubtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
