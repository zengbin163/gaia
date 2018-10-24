package test.jdk.pref;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;
import javax.swing.*;


/*
 * 功能：演示Preferences的用法，实现JFrame窗体参数的修改、保存以及导入导出到xml文件中。
 * 版本：20150807
 * 结构：PreferencesDemo[主窗体]，PreferencesDialog
 */
public class PreferencesDemo extends JFrame {

    private Preferences preferences;//配置内容

    public PreferencesDemo() {
        // 加载配置
        loadPreferences();
        // 设置窗体属性
        initFrame();
    }

    public void loadPreferences() {
        /*
         * 加载配置,它位于注册表
         */
        Preferences root = Preferences.userRoot();//HKEY_CURRENT_USER\Software\JavaSoft\Prefs
        preferences = root.node("/com/horstmann/corejava");
    }

    public void updatePreferencesValue(String key, String value){
        /*
         * 功能：更新Preferences的内容
         */
        preferences.put(key, value);
    }

    public void flushPreferences()
    {
        /*
         * 功能：将最新Preferences的值写入配置文件
         */
        try {
            preferences.flush();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    public String getPreferencesValue(String key){
        /*
         * 功能：根据key获取configProperties中对应的value
         */
        return preferences.get(key, "0");
    }

    public void exportPreferences(OutputStream out) {
        /*
         * 导出配置
         */
        try {
            preferences.exportSubtree(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    public void importPreferences(InputStream in) {
        /*
         * 导入配置
         */
        try {
            Preferences.importPreferences(in);
            in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InvalidPreferencesFormatException e1) {
            e1.printStackTrace();
        }
    }

    public void initFrame() {
        //获取参数，如果不存在则取默认值
        String left = preferences.get("left", "0");
        String top = preferences.get("top", "0");
        String width = preferences.get("width", "300");
        String height = preferences.get("height", "200");
        String title =  preferences.get("title", "default title");

        JMenuBar menubar = new JMenuBar();
        JMenu windowMenu = new JMenu("Window");
        windowMenu.setMnemonic('W');
        JMenuItem preferencesItem = new JMenuItem("Preferences");
        preferencesItem.setMnemonic('P');
        preferencesItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PreferencesDialog optionsDialog = new PreferencesDialog(PreferencesDemo.this);
                optionsDialog.setVisible(true);
            }
        });
        setJMenuBar(menubar);
        menubar.add(windowMenu);
        windowMenu.add(preferencesItem);

        setBounds(Integer.parseInt(left), Integer.parseInt(top), Integer.parseInt(width), Integer.parseInt(height));
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        PreferencesDemo preferencesDemo = new PreferencesDemo();
        preferencesDemo.setVisible(true);
    }
}
