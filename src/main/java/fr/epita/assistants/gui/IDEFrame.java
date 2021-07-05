package fr.epita.assistants.gui;

import com.formdev.flatlaf.FlatLightLaf;
import fr.epita.assistants.gui.editor.EditorPane;
import fr.epita.assistants.gui.optionmenu.ReminderLogic;
import fr.epita.assistants.gui.shell.IDEShell;
import fr.epita.assistants.gui.tree.TreePanel;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.ProjectServiceImplementation;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class IDEFrame extends JFrame {

    private EditorPane editorPane;

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


        editorPane = new EditorPane();


        add(editorPane, BorderLayout.CENTER);


        IDEMenu Bar = new IDEMenu();
        setJMenuBar(Bar);

        p = new ProjectServiceImplementation();
        currentProject = p.load(Path.of(path));
        var panel =new TreePanel(currentProject.getRootNode());
        add(panel , BorderLayout.WEST);
        panel.setLayout(new GridLayout());

        //setSize(512,512 );

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





        pack();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("logo.png")));
        setVisible(true);
        var reminder = new ReminderLogic("Remember to take a break for your happiness"
                , 15);
        reminder.scheduler();
        IDEConfig.INSTANCE.setReminder(reminder);

        JPanel top = new JPanel();
        mavenButton mavenButton = new mavenButton();
        gitButtons gitbutton = new gitButtons();
        top.add(mavenButton);
        top.add(gitbutton);
        add(top, BorderLayout.NORTH);

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

    public EditorPane getEditorPane() {
        return editorPane;
    }

    /*
    ...
    Add other listeners for events
    call objects using these functions with "this"
     */
}
