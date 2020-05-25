package de.cketti.leak.actionmodecallback

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private var someValue = 42

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.textview_second)

        val button = view.findViewById<Button>(R.id.button_second)
        button.setOnClickListener {
            startActionMode(textView)
        }
    }

    private fun startActionMode(textView: TextView) {
        val appCompatActivity = requireActivity() as AppCompatActivity
        appCompatActivity.startSupportActionMode(object : ActionMode.Callback {
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                if (someValue == 23) println("Force implicit reference to Fragment")
                return true
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                textView.text = "Please exit action mode"
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

            override fun onDestroyActionMode(mode: ActionMode?) {
                textView.text = "Press back button to destroy fragment. Then wait for LeakCanary to do its thing."
            }
        })
    }
}
