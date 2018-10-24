package test.jdk.pref;

import org.junit.Test;

import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * TODO 看起来，更像是Java应用用来保存信息的一种手段。
 * 类似Properties，但更佳。且保存位置随OS不同而不同。
 *
 * Created by 张少昆 on 2018/1/15.
 */
public class PreferencesTest {

    @Test
    public void pref() throws BackingStoreException{
        Preferences systemRoot = Preferences.systemRoot();
        Preferences userRoot = Preferences.userRoot();

        System.out.println(systemRoot);
        System.out.println(userRoot);

        System.out.println(systemRoot.name());
//        System.out.println(Arrays.toString(systemRoot.keys()));
        System.out.println(Arrays.toString(userRoot.keys()));
    }

}
