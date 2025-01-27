package org.meicode.MenuPrincipal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.meicode.handson.R;

import java.util.List;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiaViewHolder> {

    private List<Noticia> noticiasList; // List of news items
    private final Context context; // Activity or fragment context
    private final OnNoticiaListener listener; // Listener for edit and delete actions
    private final boolean esUsuario; // Indica si es un adaptador para noticias del usuario

    public NoticiasAdapter(List<Noticia> noticiasList, Context context, OnNoticiaListener listener, boolean esUsuario) {
        this.noticiasList = noticiasList;
        this.context = context;
        this.listener = listener;
        this.esUsuario = esUsuario;
    }


    @NonNull
    @Override
    public NoticiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = esUsuario ? R.layout.item_noticia_usuario : R.layout.item_noticia; // Cambiar el layout según el contexto
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new NoticiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiaViewHolder holder, int position) {
        Noticia noticia = noticiasList.get(position);

        // Vincular datos al título y contenido
        holder.tvTitulo.setText(noticia.getTitulo());
        holder.tvContenido.setText(noticia.getContenido());
        holder.tvRequisitos.setText("Requisitos: " + noticia.getRequisitos());
        holder.tvTags.setText("Tags: " + noticia.getTags());
        holder.tvAutor.setText("Autor: " + noticia.getNombreAutor() + " " + noticia.getApellidoAutor());

        // Configurar botones solo si es el layout de usuario
        if (esUsuario) {
            // Verifica que los botones no sean nulos antes de configurarlos
            if (holder.btnEditar != null) {
                holder.btnEditar.setOnClickListener(v -> {
                    if (listener != null) {
                        Intent intent = new Intent(context, EditarNoticiaActivity.class);
                        intent.putExtra("NOTICIA_ID", noticia.getId());  // Pasar el ID de la noticia
                        context.startActivity(intent);
                    }
                });
            }
            if (holder.btnEliminar != null) {
                holder.btnEliminar.setOnClickListener(v -> {
                    if (listener != null) {
                        boolean isDeleted = listener.onEliminarClick(noticia);
                        if (isDeleted) {
                            // Eliminar la noticia de la lista
                            noticiasList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, getItemCount());
                        }
                    }
                });
            }
        } else {
            // Ocultar botones si el layout no es de usuario
            if (holder.btnEditar != null) holder.btnEditar.setVisibility(View.GONE);
            if (holder.btnEliminar != null) holder.btnEliminar.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        // Return the total number of news items
        return noticiasList.size();
    }

    public void updateList(List<Noticia> nuevaLista) {
        this.noticiasList = nuevaLista; // Update the list reference
        notifyDataSetChanged(); // Notify the adapter to refresh the view
    }


    public static class NoticiaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvContenido, tvRequisitos, tvTags, tvAutor; // Campos de la noticia
        Button btnEditar, btnEliminar; // Botones de acción

        public NoticiaViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar vistas
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvContenido = itemView.findViewById(R.id.tvContenido);
            tvRequisitos = itemView.findViewById(R.id.tvRequisitos);
            tvTags = itemView.findViewById(R.id.tvTags);
            tvAutor = itemView.findViewById(R.id.tvAutor);

            // Verifica si los botones existen en el layout antes de inicializarlos
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }



    // Interface for edit and delete actions
    public interface OnNoticiaListener {
        void onEditarClick(Noticia noticia);
        boolean onEliminarClick(Noticia noticia); // Called when "Delete" is clicked
    }
}


