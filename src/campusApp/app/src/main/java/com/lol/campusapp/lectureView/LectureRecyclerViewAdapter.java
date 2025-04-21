package com.lol.campusapp.lectureView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lol.campusapp.R;
import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.utils.ActivityUtils;
import com.lol.campusapp.utils.ContextHelper;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class LectureRecyclerViewAdapter extends RecyclerView.Adapter<LectureRecyclerViewAdapter.MyViewHolder> {
    List<LectureSurfaceModel> lectureSurfaceModels;

    public LectureRecyclerViewAdapter(List<Lecture> lectures){
        lectureSurfaceModels = lectures.stream()
                .map(LectureSurfaceModel::new)
                .sorted(Comparator.comparing(LectureSurfaceModel::getTitel))
                .collect(Collectors.toList());
    }

    public void setLectureSurfaceModels(List<LectureSurfaceModel> lectureSurfaceModels) {
        this.lectureSurfaceModels = lectureSurfaceModels;
    }

    @NonNull
    @Override
    public LectureRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ContextHelper.getContext());
        View view = inflater.inflate(R.layout.lecture_recycler_view_row, parent, false);
        return new LectureRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureRecyclerViewAdapter.MyViewHolder holder, int position) {
        LectureSurfaceModel model = lectureSurfaceModels.get(position);
        holder.titel.setText(model.getTitel());
        holder.semester.setText(model.getSemester());
        holder.setVersionNR(model.getVersionNR());
    }

    @Override
    public int getItemCount() {
        return lectureSurfaceModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titel, semester;
        String VersionNR;

        public void setVersionNR(String versionNR) {
            VersionNR = versionNR;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titel = itemView.findViewById(R.id.MLtitel);
            semester = itemView.findViewById(R.id.MLsemester);

            itemView.setOnClickListener(position -> {
                Intent intent = new Intent(ContextHelper.getContext(), LectureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("lecture_verNR", VersionNR);
                ContextHelper.getContext().startActivity(intent);
            });
        }

    }
}


