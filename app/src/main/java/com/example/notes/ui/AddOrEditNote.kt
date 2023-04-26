package com.example.notes.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.notes.R
import com.example.notes.databinding.FragmentAddOrEditNoteBinding
import com.example.notes.model.Note
import com.example.notes.viewmodels.NoteViewModel
import kotlinx.coroutines.launch

class AddOrEditNote : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentAddOrEditNoteBinding
    private val viewModel: NoteViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // On Back Button Pressed.
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddOrEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val noteId = arguments?.getInt("noteId")
        if (noteId != null) {

            viewModel.getNote(noteId)

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.note.collect { note ->
                        binding.noteTitle.text = stringToEditable(note.title)
                        binding.noteContent.text = stringToEditable(note.description)
                    }
                }
            }
        }

    }

    // Create a new OnBackPressedCallback
    private val callback = object : OnBackPressedCallback(true /* enabled by default */) {
        override fun handleOnBackPressed() {
            if (binding.noteTitle.text.isNotBlank() || binding.noteContent.text.isNotBlank()) {

                val noteId = arguments?.getInt("noteId")

                if (noteId != null) {
                    viewModel.update(
                        Note(
                            binding.noteTitle.text.toString(),
                            binding.noteContent.text.toString(),
                            noteId
                        )
                    )
                } else {
                    viewModel.insert(
                        Note(
                            binding.noteTitle.text.toString(),
                            binding.noteContent.text.toString()
                        )
                    )
                }
            }
            // Navigate to a different fragment or activity
            navController.navigate(R.id.action_addOrEditNote_to_noteFragment2)
        }
    }

    // Converts the String into Editable for update method.
    private fun stringToEditable(string: String?): Editable {
        return Editable.Factory().newEditable(string)
    }
}