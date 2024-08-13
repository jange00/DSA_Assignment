package Question6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileConverterApp extends JFrame {

    private JFileChooser fileChooser;
    private JComboBox<String> formatComboBox;
    private JButton startButton, cancelButton;
    private JProgressBar progressBar;
    private JTextArea statusArea;
    private ExecutorService executorService;

    public FileConverterApp() {
        setTitle("File Converter");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Initialize components
        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setControlButtonsAreShown(false); // Hide default control buttons

        formatComboBox = new JComboBox<>(new String[]{"PDF to Docx", "Image Resize"});

        startButton = new JButton("Start");
        cancelButton = new JButton("Cancel");

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        statusArea = new JTextArea();
        statusArea.setEditable(false);

        // Set up the control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        // Add file chooser at the top of the control panel
        controlPanel.add(fileChooser, BorderLayout.NORTH);

        // Add conversion options and buttons in a separate panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.add(new JLabel("Conversion Type:"));
        optionsPanel.add(formatComboBox);
        optionsPanel.add(startButton);
        optionsPanel.add(cancelButton);

        // Add options panel to the center of the control panel
        controlPanel.add(optionsPanel, BorderLayout.CENTER);

        // Add all components to the frame
        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(progressBar, BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(statusArea), BorderLayout.CENTER);

        // Add action listeners to the buttons
        startButton.addActionListener(this::startConversion);
        cancelButton.addActionListener(this::cancelConversion);
    }

    private void startConversion(ActionEvent event) {
        File[] files = fileChooser.getSelectedFiles();
        String conversionType = (String) formatComboBox.getSelectedItem();

        executorService = Executors.newFixedThreadPool(files.length);
        progressBar.setMaximum(files.length * 100);

        for (File file : files) {
            ConversionTask task = new ConversionTask(file, conversionType);
            task.addPropertyChangeListener(new ProgressListener());
            executorService.submit(task);
        }
    }

    private void cancelConversion(ActionEvent event) {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
            statusArea.append("Conversion canceled.\n");
        }
    }

    private class ConversionTask extends SwingWorker<Void, FileConversionStatus> {
        private final File file;
        private final String conversionType;

        public ConversionTask(File file, String conversionType) {
            this.file = file;
            this.conversionType = conversionType;
        }

        @Override
        protected Void doInBackground() {
            try {
                // Simulate file conversion process
                for (int i = 0; i <= 100; i += 10) {
                    Thread.sleep(100); // Simulate time-consuming task
                    setProgress(i);
                    publish(new FileConversionStatus(file.getName(), i));
                }
            } catch (InterruptedException e) {
                publish(new FileConversionStatus(file.getName(), -1, "Conversion canceled."));
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                publish(new FileConversionStatus(file.getName(), -1, "Conversion failed."));
            }
            return null;
        }

        @Override
        protected void process(List<FileConversionStatus> chunks) {
            for (FileConversionStatus status : chunks) {
                statusArea.append(status.toString() + "\n");
            }
        }

        @Override
        protected void done() {
            statusArea.append("Conversion of " + file.getName() + " completed.\n");
        }
    }

    private class ProgressListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("progress".equals(evt.getPropertyName())) {
                int progress = (Integer) evt.getNewValue();
                progressBar.setValue(progressBar.getValue() + progress);
            }
        }
    }

    private static class FileConversionStatus {
        private final String fileName;
        private final int progress;
        private final String message;

        public FileConversionStatus(String fileName, int progress) {
            this(fileName, progress, null);
        }

        public FileConversionStatus(String fileName, int progress, String message) {
            this.fileName = fileName;
            this.progress = progress;
            this.message = message;
        }

        @Override
        public String toString() {
            return fileName + ": " + (message != null ? message : "Progress: " + progress + "%");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileConverterApp app = new FileConverterApp();
            app.setVisible(true);
        });
    }
}
