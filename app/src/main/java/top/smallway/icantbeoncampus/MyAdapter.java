package top.smallway.icantbeoncampus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private final List<Person> personList;

    public MyAdapter(List<Person> personList) {
        this.personList = personList;
    }
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myadapter, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Person person = personList.get(position);
        holder.title.setText(person.getTitle());
        if (person.getType().equals("校区签到\n(小tips：签到位置必须在学校)")){
            holder.type.setText(person.getType());
            holder.type.setBackgroundResource(R.color.gree);
        }else {
            holder.type.setText(person.getType());
            holder.type.setBackgroundResource(R.color.red);
        }

        holder.time.setText(person.getTime());
        if (person.getStatus().equals("已签到")){
            holder.status.setBackgroundResource(R.color.gree);
        }else {
            holder.status.setBackgroundResource(R.color.red);
        }
        holder.status.setText(person.getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里启动二级页面
                Intent intent = new Intent(v.getContext(), Sign.class);
                intent.putExtra("id", personList.get(position).getId());
                intent.putExtra("logId",personList.get(position).getLogId());
                intent.putExtra("title",personList.get(position).getTitle());
                intent.putExtra("time",personList.get(position).getTime());
                intent.putExtra("type",personList.get(position).getType());
                intent.putExtra("status",personList.get(position).getStatus());
                intent.putExtra("mode",personList.get(position).getMode());
                intent.putExtra("schoolId",personList.get(position).getSchoolId());
                intent.putExtra("latitude",personList.get(position).getLatitude());
                intent.putExtra("longitude",personList.get(position).getLongitude());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,type,time,status;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.Sign_title);
            this.type = itemView.findViewById(R.id.Sign_type);
            this.time = itemView.findViewById(R.id.Sign_time);
            this.status=itemView.findViewById(R.id.Sign_status);
        }
    }
}
