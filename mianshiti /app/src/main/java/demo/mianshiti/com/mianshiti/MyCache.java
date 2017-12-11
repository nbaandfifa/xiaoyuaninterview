package demo.mianshiti.com.mianshiti;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import demo.mianshiti.com.mianshiti.bean.RecordBean;

import static android.R.attr.key;
import static java.nio.file.Paths.get;

/**
 * author xmn
 * on 2017/12/9 09:54
 */

public class MyCache   {
    //最大数量
    private int maxSize;
    //超时时间
    private int  outtimesec;
    //记时时间
    private int  currentsec;
    //缓存的hashmap
    LinkedHashMap<String,Object> map;
    //记录的hashmap
    LinkedHashMap<String,RecordBean> map_record;


    private Handler  handler = new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what==0){
                currentsec++;
                //获取超时值并删除
                ArrayList list=new ArrayList();
                for (Iterator it = map_record.keySet().iterator(); it.hasNext();)
                {

                    String key = (String) it.next();
                    RecordBean  recordBean=map_record.get(key);
                    //获取记录的单个时间

                   int  thisouttimesec=currentsec-recordBean.getRecord();
                    if(thisouttimesec>outtimesec){
                        //要删除的key存入arraylist
                        list.add(key);

                    }else{
                        break;
                    }
                }
                 for(int i=0;i<list.size();i++){
                     //删除的key
                     map.remove(list.get(i));
                     map_record.remove(list.get(i));
                     Log.i("mylog","remove "+key);
                 }
            }
        }
    };;
    /**
     *实例化最大长度和超时时间
     */
    public MyCache( int maxSize,int outtimesec) {
        this.maxSize=maxSize;
        this.outtimesec=outtimesec;
        this.map = new LinkedHashMap<String,Object>();
        this.map_record = new LinkedHashMap<String,RecordBean>();
        //启动时间
        Runnable  runnable = new Runnable( ) {

            public void run ( ) {

                handler.sendEmptyMessage(0);

                handler.postDelayed(this, 1000);     //postDelayed(this,1000)方法安排一个Runnable对象到主线程队列中

            }

        };
        handler.postDelayed(runnable, 1000);
    }
    //插入值
    public void putobject(String s_num,Object object){
        //记录存入的object  和存储的时间
        map.put(s_num,object);
        //记录点击次数和存储时间
        map_record.put(s_num,new RecordBean(0,currentsec));
        if(map_record.size()>maxSize){
            String temp="";
            int temp_checknum = 0;
            for (Iterator it = map_record.keySet().iterator(); it.hasNext();)
            {

                String key = (String) it.next();
                RecordBean  recordBean=map_record.get(key);
                if(TextUtils.isEmpty(temp)){
                    //第一次进入的时候 存储第一个key和点击次数
                    temp=key;
                    temp_checknum=recordBean.getChecknum();
                }else{
                    //当遍历的点击次数小于当前最小的点击次数的时候 存储最小的次数
                    if(temp_checknum>recordBean.getChecknum()){
                        temp_checknum=recordBean.getChecknum();
                        temp=key;
                    }
                }
            }
            //删除最少点击次数的值
            map.remove(temp);
            map_record.remove(temp);
        }

    }
    //获取handler
    public Handler gethandeler(){
        return  handler;
    }
    //获取整个map
    public LinkedHashMap<String,Object> getmap(){
        return map;
    }
}
