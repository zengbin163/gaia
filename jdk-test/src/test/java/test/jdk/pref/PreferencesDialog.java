package test.jdk.pref;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * @功能：修改配置对话框
 * @版本：20150807
 */
public class PreferencesDialog extends JDialog {

    PreferencesDemo preferencesDemo;// 父窗体
    private JTextField xField;
    private JTextField yField;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField titleField;

    private JButton importButton;// 导入配置
    private JButton exportButton;// 导出配置
    private JButton saveButton;// 保存
    private JButton cancelButton;// 取消

    private JFileChooser fileChooser;// 文件选择器,用于导入导出配置

    public PreferencesDialog(PreferencesDemo parent){
        super(parent, true);
        preferencesDemo = parent;

        // 提取主配置信息，作为控件的默认值
        String xPosition = preferencesDemo.getPreferencesValue("left");
        String yPosition = preferencesDemo.getPreferencesValue("top");
        String width = preferencesDemo.getPreferencesValue("width");
        String height = preferencesDemo.getPreferencesValue("height");
        String title = preferencesDemo.getPreferencesValue("title");

        // 本UI包含2个panel
        JPanel inputPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        // 构造inputPanel
        inputPanel.setLayout(new GridLayout());

        inputPanel.add(new JLabel("xPosition:"));
        xField = (JTextField) inputPanel.add(new JTextField(xPosition));
        inputPanel.add(inputPanel.add(new JLabel("yPosition:")));
        yField = (JTextField) inputPanel.add(new JTextField(yPosition));
        inputPanel.add(inputPanel.add(new JLabel("witdh:")));
        widthField = (JTextField) inputPanel.add(new JTextField(width));
        inputPanel.add(inputPanel.add(new JLabel("height:")));
        heightField = (JTextField) inputPanel.add(new JTextField(height));
        inputPanel.add(inputPanel.add(new JLabel("title:")));
        titleField = (JTextField) inputPanel.add(new JTextField(title));

        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        // 构造buttonPanel

        importButton = new JButton("import");
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                InputStream in = null;
                if(fileChooser.showSaveDialog(PreferencesDialog.this) == JFileChooser.APPROVE_OPTION){
                    try{
                        in = new FileInputStream(fileChooser.getSelectedFile());

                    } catch(FileNotFoundException e1){
                        e1.printStackTrace();
                    }
                    preferencesDemo.importPreferences(in);//导入配置内容

                    int result = JOptionPane.showConfirmDialog(PreferencesDialog.this, "是否立即更新窗体?", "导入成功", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        validateParentWindow();//更新父窗体界面
                    }
                }
            }
        });

        exportButton = new JButton("export");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(fileChooser.showSaveDialog(PreferencesDialog.this) == JFileChooser.APPROVE_OPTION){
                    try{
                        OutputStream out = new FileOutputStream(fileChooser.getSelectedFile());
                        preferencesDemo.exportPreferences(out);//导出配置内容
                    } catch(Exception e2){
                        e2.printStackTrace();
                    }
                }
                JOptionPane.showMessageDialog(PreferencesDialog.this, "导出成功");
                setVisible(false);
            }
        });

        saveButton = new JButton("save");
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                preferencesDemo.updatePreferencesValue("left", xField.getText().trim());
                preferencesDemo.updatePreferencesValue("top", yField.getText().trim());
                preferencesDemo.updatePreferencesValue("width", widthField
                                                                        .getText().trim());
                preferencesDemo.updatePreferencesValue("height", heightField.getText().trim());
                preferencesDemo.updatePreferencesValue("title", titleField.getText().trim());

                preferencesDemo.flushPreferences();// 写入配置文件
                int result = JOptionPane.showConfirmDialog(PreferencesDialog.this, "是否立即更新窗体?", "保存成功", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    validateParentWindow();//更新父窗体界面
                }
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                setVisible(false);
            }
        });

        buttonPanel.add(importButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        // 构造主框架
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // 设置窗体属性
        setTitle("更改主窗体配置");
        setLocationRelativeTo(inputPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        // 初始化文件选择器
        initFileChooser();
    }

    private void validateParentWindow(){
        setVisible(false);
        preferencesDemo.initFrame();
        preferencesDemo.validate();
    }

    private void initFileChooser(){
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileFilter(new FileFilter() {

            @Override
            public String getDescription(){
                return "XML files";
            }

            @Override
            public boolean accept(File f){
                return f.getName().toLowerCase()
                               .endsWith(".xml")
                               || f.isDirectory();
            }
        });
    }
}