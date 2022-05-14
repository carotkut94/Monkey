package com.death.monkey.repositories

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import com.death.monkey.BuildConfig
import com.death.monkey.domain.Apps
import javax.inject.Inject

interface ApplicationsRepository {
    suspend fun getAllApps():List<Apps>
}

class ApplicationRepositoryImpl @Inject constructor(private val packageManager: PackageManager): ApplicationsRepository{

    override suspend fun getAllApps(): List<Apps> {
        val installedApps = mutableListOf<Apps>()
        for (resolveInfo in launcherActivities()) {
            val app = app(resolveInfo)
            if (app.packageName != BuildConfig.APPLICATION_ID) {
                installedApps.add(app)
            }
        }
        return installedApps
    }

    private fun launcherActivities(): List<ResolveInfo> {
        val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        val intentForLeanback = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
        return packageManager.queryIntentActivities(intent, 0).apply {
            addAll(packageManager.queryIntentActivities(intentForLeanback, 0))
        }.toList()
    }

    private fun app(resolveInfo: ResolveInfo): Apps {
        val applicationInfo = resolveInfo.activityInfo.applicationInfo
        val packageName = resolvePackageName(resolveInfo)
        val name = packageManager.getApplicationLabel(applicationInfo).toString()
        val icon = packageManager.getApplicationIcon(applicationInfo)
        return Apps(name, packageName, icon=icon)
    }

    private fun resolvePackageName(resolveInfo: ResolveInfo): String {
        return resolveInfo.activityInfo.packageName ?: resolveInfo.resolvePackageName
    }
}


