package demo.mianshiti.com.mianshiti;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Iterator;
import java.util.LinkedHashMap;

import demo.mianshiti.com.mianshiti.bean.DBHelper;
import demo.mianshiti.com.mianshiti.bean.DBrecordBean;
import demo.mianshiti.com.mianshiti.bean.IHttpCallback;

public class MainActivity extends AppCompatActivity {


 /*   Android 面试题

    Fork此项目，完成后提交pull request

    1、 实现一个内存缓存，支持设置容量和有效时长，超过有效时长的条目会被回收。如果容量不够，按照访问次数删除条目（次数少的优先删除）

    2、对Android 网络请求进行简单封装，基于HttpURLConnection，实现GET，POST，PUT，DELETE等方法

    3、 对Android Sqlite数据库操作进行封装，需要支持增删改查，灵活性高，能适应不同场景*/

    TextView textView_getmap;//获取内存
    TextView textView_putmap;//存入内存
    TextView textView_gethttp;//http封装类
    TextView textView_getdb;//db封装类

    MyCache myCache;

    //数据库存储
    private DBHelper db = null;
    private DBrecordBean rb = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCache=new MyCache(10,10);
        myCache.putobject("1","n1");
        myCache.putobject("2","n2");
        myCache.putobject("3","n3");
        myCache.putobject("4","n4");
        myCache.putobject("5","n5");
        myCache.putobject("6","n6");
        myCache.putobject("7","n7");
        myCache.putobject("8","n8");
        myCache.putobject("9","n9");
        myCache.putobject("10","n10");

        textView_getmap= (TextView) findViewById(R.id.getmap);
        textView_getmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinkedHashMap<String,Object>  map = myCache.getmap();
                for (Iterator it = map.keySet().iterator(); it.hasNext();) {
                }
            }
        });
        textView_putmap= (TextView) findViewById(R.id.putmap);
        textView_putmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCache.putobject(System.currentTimeMillis()+"","n11");
            }
        });
        /////////////////////////////////////////////

        textView_gethttp= (TextView) findViewById(R.id.putmap);
        textView_gethttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlParams = "name=Bob&age=18&weight=60"; //也可是 "{\"name\":\"Bob\",\"age\":18}"
                String url = "http://com.test.com";//也可是https://XXX

                HttpUrlConnectionUtils h = HttpUrlConnectionUtils.getHttpUtil(url, urlParams, MainActivity.this, new IHttpCallback(){

                    public void onResponse(String result) {
                        // TODO Auto-generated method stub
                        Log.d("log", "string from server: " + result);
                    }

                });

                h.httpPost();//也可是h.httpGet() h.httpPUT  h.httpDELETE
            }
        });

        /////////////////////////////////////////////

        textView_getdb= (TextView) findViewById(R.id.getdb);
        textView_getdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db == null) {
                    db = new DBHelper(MainActivity.this);
                    rb = new DBrecordBean();
                }
                rb.findMember_Me(db);	//查找我的用户信息
            }
        });

    }




}
