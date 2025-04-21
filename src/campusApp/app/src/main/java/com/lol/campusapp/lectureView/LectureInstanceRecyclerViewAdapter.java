package com.lol.campusapp.lectureView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.lol.campusapp.R;
import com.lol.campusapp.SQLite.LectureDataConnection;
import com.lol.campusapp.SQLite.UserDataConnection;
import com.lol.campusapp.calendar.EventAdapter;
import com.lol.campusapp.calendar.WeekViewActivity;
import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.data.LectureInstance;
import com.lol.campusapp.data.User;
import com.lol.campusapp.utils.ContextHelper;
import com.lol.campusapp.utils.DataUtils;

import java.util.List;
import java.util.stream.Collectors;

public class LectureInstanceRecyclerViewAdapter extends RecyclerView.Adapter<LectureInstanceRecyclerViewAdapter.MyViewHolder> {
    List<LectureInstanceModel> lectureInstanceModels;
    List<LectureInstance> lectureInstances;
    MyOnItemClickListener listener = null;

    public LectureInstanceRecyclerViewAdapter(List<LectureInstance> lectureInstances) {
        this.lectureInstances = lectureInstances;
        lectureInstanceModels = lectureInstances.stream()
                .map(LectureInstanceModel::new)
                .collect(Collectors.toList());
    }

    public void addItemOnClickListener(MyOnItemClickListener listener) {
        this.listener = listener;
    }

    public MyOnItemClickListener getListener() {
        return listener;
    }

    public interface MyOnItemClickListener{
        public void onClick(LectureInstance instance);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ContextHelper.getContext());
        View view = inflater.inflate(R.layout.lectureinstance_recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LectureInstanceModel model = lectureInstanceModels.get(position);
        holder.paralelGroupAndForm.setText(model.getParallelGroupAndForm());
        holder.room.setText(model.getRoom());
        holder.time.setText(model.getTime());
        holder.dateAndRhythm.setText(model.getDateAndRhythem());
        holder.setToggle(model.getInstance());
        if (listener != null) {
            holder.itemView.setOnClickListener(view -> listener.onClick(lectureInstances.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return lectureInstanceModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView paralelGroupAndForm, room, time, dateAndRhythm;
        View itemView;
        Button toggle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            paralelGroupAndForm = itemView.findViewById(R.id.lectureInstance_paralelGroupAndForm);
            room = itemView.findViewById(R.id.lectureInstance_Room);
            time = itemView.findViewById(R.id.lectureInstance_Time);
            dateAndRhythm = itemView.findViewById(R.id.lectureInstance_DateAndRhythm);
            toggle = itemView.findViewById(R.id.button2);
        }

        /**
         * sets Status off Add/Remove to Calendar button
         */
        public void setToggle(LectureInstance i){
            User curr = DataUtils.instance.getCurrentUser();
            for(LectureInstance lectureInstance : curr.getCalendar().getPinnedLectures()){
                if(i.getId() == lectureInstance.getId()){
                    toggle.setText(R.string.RemoveFromCalendar);
                    toggle.setOnClickListener(view -> {
                        curr.getCalendar().unPinLectureInstance(i);
                        setToggle(i);
                    });
                    return;
                }
            }
            toggle.setText(R.string.AddToCalendar);
            toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    curr.getCalendar().pinLectureInstance(i);
                    setToggle(i);
                }
            });

        }

    }

}
