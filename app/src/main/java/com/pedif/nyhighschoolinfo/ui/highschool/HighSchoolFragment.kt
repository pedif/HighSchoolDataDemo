package com.pedif.nyhighschoolinfo.ui.highschool

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.pedif.nyhighschoolinfo.R
import com.pedif.nyhighschoolinfo.common.Constants
import com.pedif.nyhighschoolinfo.databinding.FragmentHighSchoolBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * Fragment to display the full data representing a high school alongside its SAT records
 */
@AndroidEntryPoint
class HighSchoolFragment : Fragment() {

    val args: HighSchoolFragmentArgs by navArgs()

    //hiltviewmodel not compatible with savedStateHandle hence dynamic injection
    private val viewModel: HighSchoolViewModel by viewModels()
    private lateinit var binding: FragmentHighSchoolBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHighSchoolBinding.bind(
            inflater.inflate(
                R.layout.fragment_high_school,
                container,
                false
            )
        )

        viewModel.schoolTitle = args.title

        initList()
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.EXTRA_SCHOOL_TITLE, args.title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.getString(Constants.EXTRA_SCHOOL_TITLE)?.let {
            viewModel.schoolTitle = it
        }
    }

    private fun initList() {

        binding.rvSat.apply {
            addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                ).also {
                    it.setDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.list_divider_line
                        )!!
                    )
                }

            )
            adapter = SATAdapter()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.e(viewModel.schoolTitle)
        //bind the custom toolbar and add the back button functionality
        with(binding.toolBar) {
            (activity as AppCompatActivity?)!!.setSupportActionBar(this)
            val navController = findNavController()
            val appBarConfiguration = AppBarConfiguration(navController.graph)
            setupWithNavController(navController, appBarConfiguration)
        }
        //TODO setting title after orientation change not working, Use custom textview instead
        binding.toolBar.title = viewModel.schoolTitle
        initObservers()
    }

    private fun initObservers() {

        viewModel.SATs.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.pbLoading.isVisible = false
                (binding.rvSat.adapter as SATAdapter).items = it
            }
        }

        viewModel.school.observe(viewLifecycleOwner) {
            it?.let {
                val item = it
                binding.tvJson.text = Gson().toJson(item)
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Snackbar.make(binding.rvSat, it, Snackbar.LENGTH_LONG).show()
                viewModel.errorDisplayed()
            }
        }
    }

}