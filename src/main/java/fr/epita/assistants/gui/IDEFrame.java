package fr.epita.assistants.gui;

import com.formdev.flatlaf.FlatLightLaf;
import fr.epita.assistants.gui.optionmenu.Settings;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.node.File;
import fr.epita.assistants.myide.domain.service.ProjectServiceImplementation;
import lombok.SneakyThrows;
import org.assertj.core.util.Files;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class IDEFrame extends JFrame {

    private TextEditor txt;
    private IDEShell shell;
    private ProjectServiceImplementation p;
    private Project currentProject;
    @SneakyThrows
    public IDEFrame(String path) // add options to the constructor
    {

        super("BolIDE");
        FlatLightLaf.install();
        UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        var layout = new BorderLayout();
        //layout.setHgap(2);
        //layout.setVgap(2);
        setLayout(layout);

        shell = new IDEShell();
        shell.setLayout(new GridLayout());
        add(shell, BorderLayout.SOUTH);

        txt = new TextEditor();
        txt.setLayout(new GridLayout());
        add(txt, BorderLayout.CENTER);

        CompilePanel Bar = new CompilePanel();
        setJMenuBar(Bar);

        p = new ProjectServiceImplementation();
        currentProject = p.load(Path.of(path));
        var panel =new TreePanel(currentProject.getRootNode());
        add(panel , BorderLayout.WEST);

        //setSize(512,512 );

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





        pack();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("logo.png")));
        setVisible(true);
        var reminder = new Reminder("Remember to take a break for your happiness"
                , 15);
        reminder.scheduler();
        IDEConfig.INSTANCE.setReminder(reminder);

        gitButtons gitbutton = new gitButtons();
        add(gitbutton, BorderLayout.NORTH);
    }

    public TextEditor getTxt() {
        return txt;
    }

    public ProjectServiceImplementation getP() {
        return p;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    private void actionPerformed()
    {
        // add action to perform on click
    }

    public IDEShell getShell() {
        return shell;
    }

    /*
    ...
    Add other listeners for events
    call objects using these functions with "this"
     */
}
