package com.kendoui.taglib;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.kendoui.taglib.html.Div;
import com.kendoui.taglib.html.Element;
import com.kendoui.taglib.html.Li;
import com.kendoui.taglib.html.Text;
import com.kendoui.taglib.html.Ul;

@SuppressWarnings("serial")
public class PanelBarItemTag extends BodyTagSupport implements PanelBarItemTagContainer {
    private List<PanelBarItemTag> items;
    private String text;


    public PanelBarItemTag() {
        items = new ArrayList<PanelBarItemTag>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int doEndTag() throws JspException {
        PanelBarItemTagContainer parent = (PanelBarItemTagContainer)findAncestorWithClass(this, PanelBarItemTagContainer.class);

        if (parent == null) {
            throw new JspException("The <kendo:panelBarItem> tag should be nested in a <kendo:panelBar> or <kendo:panelBarItem>.");
        }

        parent.items().add(this);

        JspWriter out = pageContext.getOut();

        Li element = new Li();

        element.append(new Text(getText()));

        BodyContent bodyContent = getBodyContent();

        if (bodyContent != null) {
            appendBodyContent(element, bodyContent);
        }

        try {
            element.write(out);
        } catch (IOException exception) {
            throw new JspException(exception);
        }

        return EVAL_PAGE;
    }

    private void appendBodyContent(Element<?> element, BodyContent bodyContent) {

        String content = getBodyContent().getString();

        if (!content.isEmpty()) {
            Element<?> contentElement;

            if (items.size() > 0) {
                contentElement = new Ul();
            } else {
                contentElement = new Div();
            }

            contentElement.html(content);

            element.append(contentElement);
        }
    }

    @Override
    public List<PanelBarItemTag> items() {
        return items;
    }
}
