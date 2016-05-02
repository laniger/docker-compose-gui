package de.joshavg.dockercomposeui.process.events.listener;

import java.util.Map;
import java.util.Optional;

import javax.swing.JOptionPane;

import de.joshavg.dockercomposeui.process.context.MainWindowContext;
import de.joshavg.dockercomposeui.process.events.EventHub.EventListener;
import de.joshavg.dockercomposeui.ui.swing.ConfirmCommandDialog;

public class OpenTerminal implements EventListener {

    private String prevService = "";

    @Override
    public void onEvent(final Map<String, Object> event) {
        final MainWindowContext context = (MainWindowContext) event.get("context");

        final Optional<String> path = context.getSelectedPath();
        if (!path.isPresent()) {
            return;
        }

        final String service = JOptionPane.showInputDialog("Enter the service name", this.prevService);
        if (service != null && service.length() > 0) {
            this.prevService = service;
            ConfirmCommandDialog.run(path.get(), "x-terminal-emulator", "-e", "docker-compose", "exec", service,
                                     "bash");
        }
    }

}
