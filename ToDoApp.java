import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ToDoApp extends JFrame {
    private JTextField taskField;
    private JTextArea taskList;
    private ArrayList<String> tasks;

    public ToDoApp() {
        tasks = new ArrayList<>();

        // Create task field
        taskField = new JTextField(20);
        taskField.addActionListener(new TaskFieldListener());

        // Create task list
        taskList = new JTextArea(10, 20);
        taskList.setEditable(false);

        // Create buttons
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddButtonListener());

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new EditButtonListener());

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteButtonListener());

        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.add(taskField);
        inputPanel.add(addButton);
        inputPanel.add(editButton);
        inputPanel.add(deleteButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        // Add panel to frame
        add(panel);

        // Set frame properties
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class TaskFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            addTask();
        }
    }

    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            addTask();
        }
    }

    private void addTask() {
        String task = taskField.getText();
        if (!task.isEmpty()) {
            tasks.add(task);
            taskField.setText("");
            updateTaskList();
        }
    }

    private class EditButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = getSelectedIndex();
            if (index != -1) {
                String newTask = JOptionPane.showInputDialog("Enter new task:");
                if (newTask != null && !newTask.isEmpty()) {
                    tasks.set(index, newTask);
                    updateTaskList();
                }
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = getSelectedIndex();
            if (index != -1) {
                tasks.remove(index);
                updateTaskList();
            }
        }
    }

    private int getSelectedIndex() {
        String taskListText = taskList.getText();
        int startIndex = taskList.getCaretPosition();
        int lineNumber = getLineNumber(taskListText, startIndex);
        return lineNumber;
    }

    private int getLineNumber(String text, int position) {
        int lineNumber = 0;
        for (int i = 0; i < position; i++) {
            if (text.charAt(i) == '\n') {
                lineNumber++;
            }
        }
        return lineNumber;
    }

    private void updateTaskList() {
        StringBuilder sb = new StringBuilder();
        for (String task : tasks) {
            sb.append(task).append("\n");
        }
        taskList.setText(sb.toString());
    }

    public static void main(String[] args) {
        new ToDoApp();
    }
}