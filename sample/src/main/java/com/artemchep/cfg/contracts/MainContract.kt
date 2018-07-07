package com.artemchep.cfg.contracts

import com.artemchep.cfg.IPresenter
import com.artemchep.cfg.IView
import com.artemchep.cfg.IViewApi

/**
 * @author Artem Chepurnoy
 */
interface IMainPresenter : IPresenter<IMainViewApi> {

    fun setReadOnly(isReadOnly: Boolean)

}

/**
 * @author Artem Chepurnoy
 */
interface IMainViewApi : IViewApi {

    fun setReadOnly(isReadOnly: Boolean)

    fun setNote(note: String)

}

/**
 * @author Artem Chepurnoy
 */
interface IMainView : IView<IMainViewApi, IMainPresenter>, IMainViewApi
