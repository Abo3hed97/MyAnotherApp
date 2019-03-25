package com.example.user.myanotherapp.Mysql;

import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.myanotherapp.R;

import java.util.ArrayList;
import java.util.List;

public class MysqlTest extends AppCompatActivity {
    Dataimport d = new Dataimport();
    private TextView t;
    private Button button;
    String ttt;
    List<String> a = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_test);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        t= findViewById(R.id.mysqltest);
        listView = findViewById(R.id.listtest);
    }

    public void getdata(View view) {
        BulletAdapter user1 = new BulletAdapter(this, d.importDataBullet());
        listView.setAdapter(user1);
    }

    public void connect(View view) {
        t.setText(d.connectToDBServer());
    }

    public void export(View view) {
        Dataexporter d = new Dataexporter();

    }
}

class UserAdapter extends ArrayAdapter<UserOnline>
{
    private Context mContext;
    private List<UserOnline> user = new ArrayList<UserOnline>();
    public UserAdapter(Context context,List<UserOnline> list)
    {
        super(context,0,list);
        mContext = context;
        user = list;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem==null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_view, parent, false);
        UserOnline currentUser = user.get(position);
        TextView title = listItem.findViewById(R.id.list_title);
        title.setText(currentUser.getUsername());

        TextView text = listItem.findViewById(R.id.list_text);
        text.setText(currentUser.getEmail());
        return listItem;

    }

}

class BulletAdapter extends ArrayAdapter<Bullet>
{
    private Context mContext;
    private List<Bullet> bullet = new ArrayList<Bullet>();
    public BulletAdapter(Context context,List<Bullet> list)
    {
        super(context,0,list);
        mContext = context;
        bullet = list;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem==null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_view, parent, false);
        Bullet currentUser = bullet.get(position);
        TextView title = listItem.findViewById(R.id.list_title);
        title.setText(currentUser.getTitle());

        TextView text = listItem.findViewById(R.id.list_text);
        text.setText(currentUser.getContent());
        return listItem;

    }

}



