package com.example.notes.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.adapter.ClickListener
import com.example.notes.adapter.NoteAdapter
import com.example.notes.databinding.FragmentNoteBinding
import com.example.notes.viewmodels.NoteViewModel
import kotlinx.coroutines.launch


class NoteFragment() : Fragment(), ClickListener {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var navController: NavController
    private val viewModel: NoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteAdapter = NoteAdapter(this)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noteList.collect { list ->
                    noteAdapter.submitList(list)
                }
            }
        }

        navController = Navigation.findNavController(view)

        binding.rv.apply {
            this.adapter = noteAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        binding.fabAdd.setOnClickListener {
            navController.navigate(R.id.action_noteFragment_to_addOrEditNote)
        }
    }

    override fun onItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putInt("noteId", id)
        navController.navigate(R.id.action_noteFragment_to_addOrEditNote, bundle)
    }

    override fun onLongItemClick(id: Int) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                // Delete selected note from database
                viewModel.deleteById(id)
            }
            .setNegativeButton("No") { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}