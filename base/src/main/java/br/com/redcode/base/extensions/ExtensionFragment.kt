package br.com.redcode.base.extensions

import androidx.fragment.app.Fragment
import br.com.redcode.base.activities.BaseActivity

/**
 * Pass arguments to a Fragment without the hassle of
 * creating a static newInstance() method for every Fragment.
 *
 * Declared outside any class to have full access in any
 * part of your package.
 *
 * Usage: instanceOf<MyFragment>("foo" to true, "bar" to 0)
 * Link: https://gist.github.com/rakshakhegde/eea58a24994ac8ce27842febcc1f2ab0
 *
 * @return Returns an instance of Fragment as the specified generic type with the params applied as arguments
 */
inline fun <reified T : Fragment> instanceOf(vararg params: Pair<String, Any>) =
    T::class.java.newInstance().apply {
        arguments = bundleOf(*params)
    }

fun BaseActivity.showOnlyThisFragment(fragment: Fragment, id: Int) {
    supportFragmentManager.beginTransaction()
        .replace(id, fragment)
        .commit()
}