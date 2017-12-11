package demo.mianshiti.com.mianshiti.bean;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

public class DBrecordBean {
	public final static String TABLE_NAME="hyphenate";

	public final static String ID="_id";
	public final static String MEMBERID="memberid";//用户id
	public final static String IMUSERID="imuserid";//环信id
	public final static String NICKNAME="nickname";//昵称
	public final static String FACEIMGID="faceimgid";//头像id
	public final static String FACEIMG="faceimg";//头像url
	public final static String OTHER="other";//其他
	public final static String ISME="isme";//是不是我本人 1为是 0为不是 2为店东
	private static DBrecordBean instance;
	public final static DBrecordBean getInstance(){
		if(null==instance){
			instance=new DBrecordBean();
		}return instance;
	}
  public final  static String sqlcreateTable(){
	  StringBuffer buffer =new StringBuffer();
	  buffer.append("create table if not exists ").append(TABLE_NAME).append("(");
	  buffer.append(ID).append(" INTEGER  primary key AUTOINCREMENT,");
	  buffer.append(MEMBERID).append(" text,");
	  buffer.append(IMUSERID).append(" text,");
	  buffer.append(NICKNAME).append(" text,");
	  buffer.append(FACEIMGID).append(" text,");
	  buffer.append(FACEIMG).append(" text,");
	  buffer.append(ISME).append(" text,");
	  buffer.append(OTHER).append(" text")
	  .append(")");
	return buffer .toString();

  }
  public final static String sqldropTable(){
	  StringBuffer buffer=new StringBuffer();
	  buffer.append("create table if sxists ").append(TABLE_NAME);
	  return buffer.toString();
  }
	public final boolean saveMember(final DBHelper dbHelper, String menberid, String imuserid, String nickname,
									String faceimgid, String faceimg, String isme){
		if(TextUtils.isEmpty(menberid)){
			menberid="";
		}
		if(TextUtils.isEmpty(imuserid)){
			imuserid="";
		}
		if(TextUtils.isEmpty(nickname)){
			nickname="";
		}
		if(TextUtils.isEmpty(faceimgid)){
			faceimgid="";
		}
		if(TextUtils.isEmpty(faceimg)){
			faceimg="";
		}
		if(TextUtils.isEmpty(isme)){
			isme="";
		}
		dbHelper.open();
		ContentValues contentsValues=null;
		Cursor cursor=dbHelper.findList(TABLE_NAME, new String[]{
				ID, IMUSERID,FACEIMG,NICKNAME},  IMUSERID+"="+"\""+imuserid+"\"",null,null, null,  ID+" asc");
		String cur_imuserid="";
		String cur_faceimg="";
		String cur_nickname="";
		while(cursor.moveToNext()){
			int id= cursor.getInt(cursor.getColumnIndex(ID));
			cur_imuserid=cursor.getString(cursor.getColumnIndex(IMUSERID));
			cur_faceimg=cursor.getString(cursor.getColumnIndex(FACEIMG));
			cur_nickname=cursor.getString(cursor.getColumnIndex(NICKNAME));
			break;
		}
		cursor.close();
		if(TextUtils.isEmpty(cur_imuserid)){
			contentsValues=new ContentValues();
			contentsValues.put(MEMBERID,menberid);
			contentsValues.put(IMUSERID, imuserid);
			contentsValues.put(NICKNAME, nickname);
			contentsValues.put(FACEIMGID, faceimgid);
			contentsValues.put(FACEIMG, faceimg);
			contentsValues.put(ISME, isme);

			dbHelper.insert(TABLE_NAME, contentsValues);

		}else {
		//if(!cur_faceimg.equals(faceimg)||!cur_nickname.equals(nickname)){


			contentsValues=new ContentValues();
			contentsValues.put(MEMBERID,menberid);
			contentsValues.put(NICKNAME, nickname);
			contentsValues.put(FACEIMGID, faceimgid);
			contentsValues.put(FACEIMG, faceimg);
			contentsValues.put(ISME, isme);
			dbHelper.update(TABLE_NAME, contentsValues, IMUSERID + "="+"\""+imuserid+"\"",
					null);


		}
		dbHelper.closeclose();
		return true;
	}
	//查找我的用户信息
	public final void  findMember_Me(final DBHelper dbHelper){
		dbHelper.open();

		Cursor cursor=dbHelper.findList(TABLE_NAME, new String[]{
				ID, MEMBERID,IMUSERID,NICKNAME,FACEIMGID,FACEIMG,ISME},  ISME+"="+"\""+"1"+"\"",null,null, null,  null);

		while(cursor.moveToNext()){
			int id=cursor.getInt(cursor.getColumnIndex(ID));
			String memberid=cursor.getString(cursor.getColumnIndex(MEMBERID));
			String imuserid=cursor.getString(cursor.getColumnIndex(IMUSERID));
			String nickname=cursor.getString(cursor.getColumnIndex(NICKNAME));
			String faceimgid=cursor.getString(cursor.getColumnIndex(FACEIMGID));
			String faceimg=cursor.getString(cursor.getColumnIndex(FACEIMG));
			String isme=cursor.getString(cursor.getColumnIndex(ISME));

			break;
		}

		cursor.close();
		dbHelper.closeclose();
	}
	//查找他人的用户信息
	public final void  findMember_other(final DBHelper dbHelper,String imuserid){
		dbHelper.open();

		Cursor cursor=dbHelper.findList(TABLE_NAME, new String[]{
				ID, MEMBERID,IMUSERID,NICKNAME,FACEIMGID,FACEIMG,ISME},  IMUSERID+"="+"\""+imuserid+"\"",null,null, null,  null);
		while(cursor.moveToNext()){
			int id=cursor.getInt(cursor.getColumnIndex(ID));
			String memberid=cursor.getString(cursor.getColumnIndex(MEMBERID));
			String nickname=cursor.getString(cursor.getColumnIndex(NICKNAME));
			String faceimgid=cursor.getString(cursor.getColumnIndex(FACEIMGID));
			String faceimg=cursor.getString(cursor.getColumnIndex(FACEIMG));
			String isme=cursor.getString(cursor.getColumnIndex(ISME));
			break;
		}
		cursor.close();
		dbHelper.closeclose();
	}
	//查找他人的用户信息
	public final void  findMember_other_all(final DBHelper dbHelper){
		dbHelper.open();

		Cursor cursor=dbHelper.findList(TABLE_NAME, new String[]{
				ID, MEMBERID,IMUSERID,NICKNAME,FACEIMGID,FACEIMG,ISME},  null,null,null, null,  null);
		Log.i("mylog","------------------");
		while(cursor.moveToNext()){
			int id=cursor.getInt(cursor.getColumnIndex(ID));
			String memberid=cursor.getString(cursor.getColumnIndex(MEMBERID));
			String nickname=cursor.getString(cursor.getColumnIndex(NICKNAME));
			String faceimgid=cursor.getString(cursor.getColumnIndex(FACEIMGID));
			String faceimg=cursor.getString(cursor.getColumnIndex(FACEIMG));
			String isme=cursor.getString(cursor.getColumnIndex(ISME));

			//	break;
		}
		Log.i("mylog","------------------");
		cursor.close();
		dbHelper.closeclose();
	}

}
