package fr.epita.assistants.gui.toolbar;

import fr.epita.assistants.gui.ArgumentDialog;
import fr.epita.assistants.gui.IDEConfig;
import fr.epita.assistants.myide.domain.entity.Mandatory;


import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import static fr.epita.assistants.gui.utils.CreateIcon.createIcon;

public class CompileButton extends JButton implements ActionListener {
    // all this field are here to redirect sout/serr into popup
    ByteArrayOutputStream outStream;
    ByteArrayOutputStream errStream;
    StringBuilder outLog = new StringBuilder();
    StringBuilder errLog = new StringBuilder();
    PrintStream newOut = null;
    PrintStream oldOut = null;
    PrintStream newErr = null;
    PrintStream oldErr = null;

    public void StartRedirectError()
    {
        outStream = new ByteArrayOutputStream();
        newOut = new PrintStream(outStream);
        oldOut = System.out;
        System.setOut(newOut);


        errStream = new ByteArrayOutputStream();
        newErr = new PrintStream(errStream);
        oldErr = System.err;
        System.setOut(newErr);
    }

    public void StopRedirectError()
    {
        outLog.setLength(0);
        errLog.setLength(0);

        System.out.flush();
        System.setOut(oldOut);
        outLog.append(outStream.toString());

        System.err.flush();
        System.setOut(oldErr);
        errLog.append(errStream.toString());
    }

    public CompileButton()
    {
        Border emptyBorder = BorderFactory.createEmptyBorder();
        setMaximumSize(new Dimension(35,35));
        setBorder(emptyBorder);
        setIcon(createIcon("cog.png", 30, 30));
        addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

            ArgumentDialog argDialog = new ArgumentDialog( "Compile");
            var args = argDialog.getArg();
            if(args != null) {
                var proj = IDEConfig.INSTANCE.getFrame().getCurrentProject();


                StartRedirectError();


                var executeReport = IDEConfig.INSTANCE.getFrame().getP().execute(proj, Mandatory.Features.Maven.COMPILE, args);



                if (executeReport.isSuccess())
                {

                    var url = System.getProperty("user.dir") + File.separator + "yes.wav";
                    URL res = null;
                    try {
                        res = Paths.get(url).toUri().toURL();
                    } catch (MalformedURLException malformedURLException) {
                        malformedURLException.printStackTrace();
                    }

                    Player p = null;

                    try {
                        p = Manager.createRealizedPlayer(res);
                    } catch (IOException | NoPlayerException | CannotRealizeException ioException) {
                        System.err.println("Failed to load file: " + res);
                    }

                    p.start();

                    StopRedirectError();

                    var compilationPopup = new CompilationPopup(true, "Compilation success");
                    compilationPopup.setVisible(true);
                    JOptionPane.showMessageDialog(this, outLog.toString() + "\n" + errLog.toString());
                }
                else
                {
                    var url = System.getProperty("user.dir") + File.separator + "bruh.wav";
                    URL res = null;
                    try {
                        res = Paths.get(url).toUri().toURL();
                    } catch (MalformedURLException malformedURLException) {
                        malformedURLException.printStackTrace();
                    }


                    Player p = null;

                    try {
                        p = Manager.createRealizedPlayer(res);
                    } catch (IOException | NoPlayerException | CannotRealizeException ioException) {
                        System.err.println("Failed to load file: " + res);
                    }

                    p.start();

                    var compilationPopup = new CompilationPopup(false, "Compilation failed");
                    compilationPopup.setVisible(true);


                    StopRedirectError();
                    JOptionPane.showMessageDialog(this, outLog.toString() + "\n" + errLog.toString());
                }
            }
        }

}
