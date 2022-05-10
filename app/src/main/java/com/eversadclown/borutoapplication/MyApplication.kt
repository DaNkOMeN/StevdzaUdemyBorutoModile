package com.eversadclown.borutoapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//необходимо добавить этот класс, для того, чтобы работана DI от dagger
@HiltAndroidApp
class MyApplication : Application()