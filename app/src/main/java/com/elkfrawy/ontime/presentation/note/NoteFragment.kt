package com.elkfrawy.ontime.presentation.note

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elkfrawy.ontime.data.constant.EDIT_NOTE
import com.elkfrawy.ontime.data.constant.EDIT_NOTE_VALUE
import com.elkfrawy.ontime.data.constant.EXTRA_NOTE
import com.elkfrawy.ontime.databinding.FragmentNoteBinding
import com.elkfrawy.ontime.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment(), NoteRecyclerAdapter.OnItemClick {

    val adapter: NoteRecyclerAdapter by lazy { NoteRecyclerAdapter() }
    private val viewModel: NoteViewModel by viewModels()
    lateinit var binding: FragmentNoteBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.noteRecyclerView.adapter = adapter
        viewModel.searchFlow.value = ""

        viewModel.notesLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.searchNote.doOnTextChanged { text, _, _, _ ->
            viewModel.searchFlow.value = text.toString()
        }

        deleteTask()
        adapter.setOnItemClickListener(this)
    }
    private fun deleteTask(){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = adapter.currentList[viewHolder.adapterPosition]
                viewModel.executeDelete(note)
            }
        }).attachToRecyclerView(binding.noteRecyclerView)
    }

    override fun onItemClickListener(note: Note) {
        val intent = Intent(requireContext(), AddEditNoteActivity::class.java)
        intent.putExtra(EDIT_NOTE, EDIT_NOTE_VALUE)
        intent.putExtra(EXTRA_NOTE, note)
        startActivity(intent)
    }
}