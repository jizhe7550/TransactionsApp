package com.joe.transactionsapp.framework.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.joe.transactionsapp.framework.presentation.MainActivity
import com.joe.transactionsapp.framework.presentation.UIController
import com.joe.transactionsapp.framework.presentation.util.fadeIn
import com.joe.transactionsapp.framework.presentation.util.fadeOut
import com.joe.transactionsapp.framework.presentation.util.gone
import com.joe.transactionsapp.framework.presentation.util.visible
import com.joe.transactionsapp.util.TodoCallback
import java.lang.ClassCastException

/**
 * Abstract Fragment which binds [ViewModel] [VM] and [ViewBinding] [VB]
 */
abstract class BaseFragment<VM : ViewModel, VB : ViewBinding>(layoutId: Int) : Fragment(layoutId) {

    lateinit var uiController: UIController

    protected abstract val viewModel: VM

    private var _binding: VB? = null

    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupChannel()
    }

    abstract fun setupChannel()

    abstract fun subscribeObservers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = initBinding(view)
        init()
        subscribeObservers()
    }

    /**
     * init [_binding]
     */
    abstract fun initBinding(view: View): VB

    abstract fun init()

    override fun onDestroyView() {
        //_binding = null
        super.onDestroyView()
    }

    fun displayToolbarTitle(
        textView: TextView,
        title: String?,
        backIB: ImageButton? = null,
        useAnimation: Boolean = true
    ) {
        if (title != null) {
            showToolbarTitle(textView, title,backIB, useAnimation)
        } else {
            hideToolbarTitle(textView, useAnimation)
        }
    }

    private fun hideToolbarTitle(textView: TextView, animation: Boolean) {
        if (animation) {
            textView.fadeOut(
                object : TodoCallback {
                    override fun execute() {
                        textView.text = ""
                    }
                }
            )
        } else {
            textView.text = ""
            textView.gone()
        }
    }

    private fun showToolbarTitle(
        textView: TextView,
        title: String,
        backIB: ImageButton?,
        animation: Boolean
    ) {
        backIB?.apply {
            visible()
            backIB.setOnClickListener {
                goBack()
            }
        }
        textView.text = title
        if (animation) {
            textView.fadeIn()
        } else {
            textView.visible()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUIController(null) // null in production
    }

    protected fun goBack() {
        findNavController().popBackStack()
    }

    private fun setUIController(mockController: UIController?) {

        // TEST: Set interface from mock
        if (mockController != null) {
            this.uiController = mockController
        } else { // PRODUCTION: if no mock, get from context
            activity?.let {
                if (it is MainActivity) {
                    try {
                        uiController = activity as UIController
                    } catch (e: ClassCastException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
