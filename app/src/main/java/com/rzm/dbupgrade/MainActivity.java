package com.rzm.dbupgrade;

import android.Manifest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rzm.databaseupgrade.UpdateManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    UpdateManager updateManager;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        updateManager = new UpdateManager();
    }

    public void createV001(View view) {
        IDaoSupport<User> dao = DaoSupportFactory.getFactory(this).getDao(User.class);

        for (int i1 = 0; i1 < 10; i1++) {
            User user = new User();
            user.name = "张三-----V001 " + (i1);
            user.password = "123456";
            user.user_id = (int) System.currentTimeMillis();
            dao.insert(user);
        }


        IDaoSupport<Photo> photoIDaoSupport = DaoSupportFactory.getFactory(this).getDao(Photo.class);

        for (int i1 = 0; i1 < 10; i1++) {
            Photo photo = new Photo();
            photo.path = ("data/data/my.jpg-----V001 "+i1);
            photo.time = (int) System.currentTimeMillis();
            photoIDaoSupport.insert(photo);
        }
        updateManager.saveVersionInfo("V001");
    }

    public void update1to2(View view) {
        /**
         * 这个方法是模拟一个情景，当前有一个新版本发布，此时运行于市场上的app通过版本更新
         * 接口请求到了新版本信息，新版本版本号为V003,当前版本版本号为V002,通过这个方法将新旧版本
         * 号写入到一个文件中做记录，这个信息可以传递出此次升级是从哪个版本升级到哪个版本
         * 保存成功返回true，否则返回false
         */
        updateManager.startUpdateDb(this);
    }

    public void update1to3(View view) {
        updateManager.startUpdateDb(this);
    }

    public class User {

        //---------------V001
        public String name;

        public String password;

        public int user_id;

        //---------------V002新增
        //public String lastLoginTime;
    }

    public class Photo {
        //-------------V001
        public int time;

        public String path;

        //-------------V002新增
        //public int size;
    }
}
