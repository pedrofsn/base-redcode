package br.com.redcode.easyrestful.library.fragment

import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.easyrestful.library.domain.BaseFragmentMVVMRestful

abstract class FragmentMVVM<VM : AbstractBaseViewModel> : BaseFragmentMVVMRestful<VM>()