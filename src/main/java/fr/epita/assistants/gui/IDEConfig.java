package fr.epita.assistants.gui;

import java.awt.*;
import fr.epita.assistants.myide.domain.entity.Node;
import java.util.ArrayList;

public enum IDEConfig {
    INSTANCE;

    private final IDEFrame frame = new IDEFrame();
    private final ArrayList<Node> nodes = new ArrayList<>();

    public IDEFrame getFrame() {
        return frame;
    }

    public void setFont(String font)
    {
        frame.getTxt().setFont(font);

    }
    public void setTextSize(int textSize)
    {
        frame.getTxt().setTextSize(textSize);
    }

    public void setContent(String content)
    {
        frame.getTxt().getText().setText(content);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
