/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.bestiarum;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author vikus
 */
public class View extends JFrame {

    private final ChangeInfo changeInfo;
    private final String dir;
    private final FileNameExtensionFilter filter = new FileNameExtensionFilter("XML, YAML, JSON Files", "xml", "yaml", "json");
    private final JTree creatureTree;
    private final JScrollPane scrollP;
    private String lastImportedFormat;

    public View(ChangeInfo changeInfo) throws URISyntaxException {

        this.changeInfo = changeInfo;
        this.dir = new File("C:\\Users\\vikus\\OneDrive\\Документы\\GitHub\\Bestiarum\\Bestiarum\\Resourses").getAbsolutePath();

        JFrame frame = new JFrame("Bestiarum");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLayout(new BorderLayout());

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Бестиарий");
        DefaultTreeModel modelTree = new DefaultTreeModel(root);
        creatureTree = new JTree(modelTree);
        creatureTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath path = ((JTree) e.getSource()).getSelectionPath();
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if (node.isLeaf() && node.getLevel() == 1) {
                        InfoWindow(node);
                    }
                }
            }
        });

        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

        scrollP = new JScrollPane(creatureTree);
        scrollP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton importBtn = new JButton("Импортировать");
        importBtn.addActionListener((ActionEvent e) -> {
            String[] paths = getPathsForReading();
            if (paths != null && paths.length > 0) {
                root.removeAllChildren();
                boolean anyImportSuccess = false;
                StringBuilder errorMessages = new StringBuilder();
                for (String path : paths) {
                    if (isFileEmpty(path)) {
                        JOptionPane.showMessageDialog(null, "Файл " + path + " пустой. Пожалуйста, выберите другой файл.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    try {
                        List<Creature> creatures = changeInfo.importData(path);
                        for (Creature creature : creatures) {
                            root.add(new DefaultMutableTreeNode(creature));
                        }
                        anyImportSuccess = true;
                        lastImportedFormat = getFileExtension(path);
                    } catch (Exception ex) {
                        errorMessages.append("Ошибка при импорте файла: ").append(path).append("\n").append(ex.getMessage()).append("\n\n");
                    }
                }
                if (!anyImportSuccess) {
                    JOptionPane.showMessageDialog(null, "Не удалось импортировать ни один файл.\n" + errorMessages.toString(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else if (errorMessages.length() > 0) {
                    JOptionPane.showMessageDialog(null, "Некоторые файлы не были импортированы:\n" + errorMessages.toString(), "Предупреждение", JOptionPane.WARNING_MESSAGE);
                }
                modelTree.reload();
            }
        });

        JButton exportBtn = new JButton("Экспортировать");
        exportBtn.addActionListener((e) -> {
    if (lastImportedFormat != null) {
        String path = getPathForExport();
        if (path != null) {
            int dotIndex = path.lastIndexOf('.');
           
            if (dotIndex == -1) {
                
                path += lastImportedFormat;
            } else if (!path.substring(dotIndex).equalsIgnoreCase(lastImportedFormat)) {
               
                path = path.substring(0, dotIndex) + lastImportedFormat;
            }
            try {
                changeInfo.exportData(path);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ошибка при экспорте файла: " + path + "\n" + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Сначала импортируйте файл, чтобы установить формат.", "Ошибка", JOptionPane.WARNING_MESSAGE);
    }
});
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.add(importBtn);
        btnPanel.add(Box.createHorizontalGlue());
        btnPanel.add(exportBtn);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(scrollP, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private String[] getPathsForReading() {
        JFileChooser fileChooser = new JFileChooser(dir);
        fileChooser.setFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(true);
        int ret = fileChooser.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            String[] paths = new String[selectedFiles.length];
            for (int i = 0; i < selectedFiles.length; i++) {
                paths[i] = selectedFiles[i].getAbsolutePath();
            }
            return paths;
        }
        return null;
    }

    private String getPathForExport() {
    JFileChooser fileChooser = new JFileChooser(dir);
        fileChooser.setFileFilter(filter);
        int ret = fileChooser.showSaveDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            return path; 
        }
        return null;
    
        }

    private void InfoWindow(DefaultMutableTreeNode node) {
        Creature creature = (Creature) node.getUserObject();

        JFrame frame = new JFrame("Информация о существе");
        frame.setSize(1000, 550);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        String[][] labelsAndValues = {
            {"Идентификатор:", Integer.toString(creature.getId())},
            {"Название:", creature.getName()},
            {"Описание:", creature.getDescription()},
            {"Уровень опасности:", Integer.toString(creature.getDangerLevel())},
            {"Места обитания:", creature.getHabitat()},
            {"Активен:", creature.getActivity()},
            {"Первое упоминание:", creature.getFirstMentioned()},
            {"Иммунитеты:", creature.getImmunities()},
            {"Уязвимости:", creature.getVulnerabilities()},
            {"Рост:", creature.getHeight()},
            {"Вес:", creature.getWeight()},
            {"Рецепт средства (зелье, масло, яд):", creature.getPoisonRecipe()},
            {"Время приготовления:", Integer.toString(creature.getTime()) + " минут"},
            {"Эффективность:", creature.getEfficiency()},
            {"Источник данных:", creature.getRecievedFrom() + " файл"}
        };

        for (int i = 0; i < labelsAndValues.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(new JLabel(labelsAndValues[i][0]), gbc);
            gbc.gridx = 1;
            JLabel valueLabel = new JLabel(labelsAndValues[i][1]);
            panel.add(valueLabel, gbc);
        }

        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        frame.add(panel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton editButton = new JButton("Редактировать данные");
        btnPanel.add(editButton);
        editButton.addActionListener((e) -> {
            editWindow(node);
            frame.dispose();
        });
        frame.add(btnPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void editWindow(DefaultMutableTreeNode node) {
        Creature creature = (Creature) node.getUserObject();

        JFrame frame = new JFrame("Изменение данных о существе");
        frame.setSize(600, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 0));

        JPanel panel1 = new JPanel();
        Integer[] dangerOptions = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        JComboBox danger = new JComboBox(dangerOptions);
        danger.setSelectedIndex(creature.getDangerLevel());
        JLabel dangerLabel = new JLabel("Новое значение уровня опасности: ");
        panel1.add(dangerLabel);
        panel1.add(danger);

        JLabel vulnerabilityLabel = new JLabel("Новая информация об уязвимостях: ");
        JTextArea vulnerabilityArea = new JTextArea(creature.getVulnerabilities(), 3, 15);
        JScrollPane scrollPane = new JScrollPane(vulnerabilityArea);
        JPanel panel2 = new JPanel();
        panel2.add(vulnerabilityLabel);
        panel2.add(scrollPane);

        panel.add(panel1);
        panel.add(panel2);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));

        JPanel btnPanel = new JPanel();
        JButton okBtn = new JButton("Сохранить");
        okBtn.addActionListener((e) -> {
            int selectedDangerLevel = (int) danger.getSelectedItem();
            String newVulnerabilities = vulnerabilityArea.getText();
            creature.setDangerLevel(selectedDangerLevel);
            creature.setVulnerabilities(newVulnerabilities);
            node.setUserObject(creature);
            ((DefaultTreeModel) creatureTree.getModel()).nodeChanged(node);
            changeInfo.saveToStorage(creature.getId(), newVulnerabilities, selectedDangerLevel, creature.getRecievedFrom());
            frame.dispose();
        });
        btnPanel.add(okBtn);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    private boolean isFileEmpty(String path) {
    File file = new File(path);
    return file.exists() && file.length() == 0;
    }
    private String getFileExtension(String path) {
        int dotIndex = path.lastIndexOf('.');
        return (dotIndex == -1) ? "" : path.substring(dotIndex);
    }


}
     