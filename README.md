there is an app called turbo alarm for android and wearos.the app has a phone companion app and a wearos app. when an alarm is se on phone, the alarm goes off both in the phone app of turbo alarm as well as the watch app of turbo alarm. now when the alarm is dismissed from wither the watch or phone, it is dismissed from both the phone and watch. i want to create a similar app with a similar implementation.
to understand how the original app (Turbo Alarm ) works, i have captured logs from logcat on phone and watch both, from the time alarm is created to the time alarm goes off and then dismissed from the watch.

here is the full logcat entry for the turbo alarm phone app from the time alarm is se to the time alarm goes off and is then dismissed from the watch UI

2025-06-23 14:47:23.986  3867-24714 AiAiEcho                com.google.android.as                I  AppIndexer Package:[com.turbo.alarm] UserProfile:[0] Enabled:[true].
2025-06-23 14:47:23.986  3867-24714 AiAiEcho                com.google.android.as                I  AppFetcherImplV2 updateApps package:[com.turbo.alarm], userId:[0], reason:[package is updated.].
2025-06-23 14:47:24.359  9486-9506  AlphabeticIndexCompat   com...le.android.apps.nexuslauncher  D  computeSectionName: cs: Turbo Alarm sectionName: T
2025-06-23 14:47:24.371  9486-9506  PackageUpdatedTask      com...le.android.apps.nexuslauncher  D  Package updated: mOp=UPDATE packages=[com.turbo.alarm], user=UserHandle{0}
2025-06-23 14:47:24.448  9486-9506  AlphabeticIndexCompat   com...le.android.apps.nexuslauncher  D  computeSectionName: cs: Turbo Alarm sectionName: T
2025-06-23 14:47:24.968 16193-16293 WearableConn            com.google.android.gms.persistent    I  Setting alarm for 256.0 seconds to retry connection
2025-06-23 14:47:24.972  9786-9786  VRI[Detail...mActivity] com.turbo.alarm                      D  visibilityChanged oldVisibility=true newVisibility=false
2025-06-23 14:47:24.985 16193-16293 AlarmManager            com.google.android.gms.persistent    I  setExactAndAllowWhileIdle [name: WearableBluetooth type: 2 triggerAtMillis: 157403587]
2025-06-23 14:47:25.019  9786-9786  ImeBackDispatcher       com.turbo.alarm                      D  Clear (mImeCallbacks.size=0)
2025-06-23 14:47:25.019  9786-9786  ImeBackDispatcher       com.turbo.alarm                      D  switch root view (mImeCallbacks.size=0)
2025-06-23 14:47:25.476  9786-9860  WM-WorkerWrapper        com.turbo.alarm                      I  Worker result SUCCESS for Work [ id=7bd31680-43ba-4d94-86ab-68a7b16fc429, tags={ com.turbo.alarm.server.workers.DevicesDownloadWorker, DevicesDownloadWorker } ]
2025-06-23 14:47:25.477  9786-9860  WM-WorkerWrapper        com.turbo.alarm                      I  Setting status to enqueued for e8018842-d06f-4149-a764-1f3629f93e8a
2025-06-23 14:47:25.483  9786-9791  com.turbo.alarm         com.turbo.alarm                      W  Reducing the number of considered missed Gc histogram windows from 251 to 100
2025-06-23 14:47:25.493  9786-9860  JobInfo                 com.turbo.alarm                      W  Requested important-while-foreground flag for job4422 is ignored and takes no effect
2025-06-23 14:47:25.493  1370-4615  JobScheduler            system_server                        W  Job didn't exist in JobStore: df7de63 #u0a312/4421 com.turbo.alarm/androidx.work.impl.background.systemjob.SystemJobService
2025-06-23 14:47:25.509  1370-2588  ConnectivityService     system_server                        D  requestNetwork for uid/pid:10312/9786 activeRequest: null callbackRequest: 5786 [NetworkRequest [ REQUEST id=5787, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]] callback flags: 0 order: 2147483647 isUidTracked: false declaredMethods: AVAIL|LOST|NC
2025-06-23 14:47:25.511  1370-2050  OemPaidWif...orkFactory system_server                        D  got request NetworkRequest [ REQUEST id=5787, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:25.511  1370-2050  MultiInter...orkFactory system_server                        D  got request NetworkRequest [ REQUEST id=5787, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:25.511  1370-2050  WifiNetworkFactory      system_server                        D  got request NetworkRequest [ REQUEST id=5787, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:25.511  1370-2050  UntrustedW...orkFactory system_server                        D  got request NetworkRequest [ REQUEST id=5787, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:25.530  9786-9853  WM-WorkerWrapper        com.turbo.alarm                      I  Worker result SUCCESS for Work [ id=e8018842-d06f-4149-a764-1f3629f93e8a, tags={ com.turbo.alarm.server.workers.DevicesUploadWorker, DevicesUploadWorker } ]
2025-06-23 14:47:25.531  9786-9853  WM-WorkerWrapper        com.turbo.alarm                      I  Setting status to enqueued for 210f9290-2b23-4719-a9a6-5b89b77202ae
2025-06-23 14:47:25.544  9786-9861  JobInfo                 com.turbo.alarm                      W  Requested important-while-foreground flag for job4423 is ignored and takes no effect
2025-06-23 14:47:25.552  1370-2588  JobScheduler            system_server                        W  Job didn't exist in JobStore: 63967f5 #u0a312/4422 com.turbo.alarm/androidx.work.impl.background.systemjob.SystemJobService
2025-06-23 14:47:25.556  1370-2588  ConnectivityService     system_server                        D  requestNetwork for uid/pid:10312/9786 activeRequest: null callbackRequest: 5788 [NetworkRequest [ REQUEST id=5789, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]] callback flags: 0 order: 2147483647 isUidTracked: false declaredMethods: AVAIL|LOST|NC
2025-06-23 14:47:25.574  1370-2050  OemPaidWif...orkFactory system_server                        D  got request NetworkRequest [ REQUEST id=5789, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:25.574  1370-2050  MultiInter...orkFactory system_server                        D  got request NetworkRequest [ REQUEST id=5789, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:25.575  1370-2050  WifiNetworkFactory      system_server                        D  got request NetworkRequest [ REQUEST id=5789, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:25.576  1370-2050  UntrustedW...orkFactory system_server                        D  got request NetworkRequest [ REQUEST id=5789, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:26.124  9786-9861  WM-WorkerWrapper        com.turbo.alarm                      I  Worker result SUCCESS for Work [ id=210f9290-2b23-4719-a9a6-5b89b77202ae, tags={ com.turbo.alarm.server.workers.AlarmDownloadWorker, AlarmDownloadWorker } ]
2025-06-23 14:47:26.125  9786-9861  WM-WorkerWrapper        com.turbo.alarm                      I  Setting status to enqueued for bc3e032b-b731-47a7-8626-9283a6131a62
2025-06-23 14:47:26.141  9786-9861  JobInfo                 com.turbo.alarm                      W  Requested important-while-foreground flag for job4424 is ignored and takes no effect
2025-06-23 14:47:26.142  1370-1600  JobScheduler            system_server                        W  Job didn't exist in JobStore: 5fd2fcf #u0a312/4423 com.turbo.alarm/androidx.work.impl.background.systemjob.SystemJobService
2025-06-23 14:47:26.173  1370-2588  ConnectivityService     system_server                        D  requestNetwork for uid/pid:10312/9786 activeRequest: null callbackRequest: 5790 [NetworkRequest [ REQUEST id=5791, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]] callback flags: 0 order: 2147483647 isUidTracked: false declaredMethods: AVAIL|LOST|NC
2025-06-23 14:47:26.189  1370-2050  OemPaidWif...orkFactory system_server                        D  got request NetworkRequest [ REQUEST id=5791, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:26.190  1370-2050  MultiInter...orkFactory system_server                        D  got request NetworkRequest [ REQUEST id=5791, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:26.192  1370-2050  WifiNetworkFactory      system_server                        D  got request NetworkRequest [ REQUEST id=5791, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:26.192  1370-2050  UntrustedW...orkFactory system_server                        D  got request NetworkRequest [ REQUEST id=5791, [ Capabilities: INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VCN_MANAGED&NOT_BANDWIDTH_CONSTRAINED Uid: 10312 RequestorUid: 10312 RequestorPkg: com.turbo.alarm UnderlyingNetworks: Null] ]
2025-06-23 14:47:26.716  9786-9855  JobInfo                 com.turbo.alarm                      W  Requested important-while-foreground flag for job4425 is ignored and takes no effect
2025-06-23 14:47:26.722  9786-9855  WM-WorkerWrapper        com.turbo.alarm                      I  Worker result SUCCESS for Work [ id=bc3e032b-b731-47a7-8626-9283a6131a62, tags={ com.turbo.alarm.server.workers.AlarmUploadWorker, AlarmUploadWorker } ]
2025-06-23 14:47:26.740  1370-2271  JobScheduler            system_server                        W  Job didn't exist in JobStore: 6a03348 #u0a312/4424 com.turbo.alarm/androidx.work.impl.background.systemjob.SystemJobService
2025-06-23 14:47:26.748  9786-9861  WM-WorkerWrapper        com.turbo.alarm                      I  Worker result SUCCESS for Work [ id=3fac669c-42ec-4289-9059-6617ecc795af, tags={ com.turbo.alarm.glance.UpdateAppWidgetWorker } ]
2025-06-23 14:47:26.765  1370-2588  JobScheduler            system_server                        W  Job didn't exist in JobStore: 4ce8019 #u0a312/4425 com.turbo.alarm/androidx.work.impl.background.systemjob.SystemJobService
2025-06-23 14:47:27.026  9786-24855 FA                      com.turbo.alarm                      I  Application backgrounded at: timestamp_millis: 1750670245014
2025-06-23 14:47:27.752  1370-1755  AppsFilter              system_server                        I  interaction: PackageSetting{2497229 com.neolabs.alarmsync.phone/10426} -> PackageSetting{99f24ea com.turbo.alarm/10312} BLOCKED
2025-06-23 14:47:27.752  1370-1755  AppsFilter              system_server                        I  interaction: PackageSetting{6f66b86 com.google.android.microdroid.empty_payload/10263} -> PackageSetting{99f24ea com.turbo.alarm/10312} BLOCKED
2025-06-23 14:47:27.788  1370-2125  ShortcutService         system_server                        D  changing package: com.turbo.alarm userId=0
2025-06-23 14:47:27.796  3867-24716 AiAiEcho                com.google.android.as                I  AppIndexer Package:[com.turbo.alarm] UserProfile:[0] Enabled:[true].
2025-06-23 14:47:27.796  3867-24716 AiAiEcho                com.google.android.as                I  AppFetcherImplV2 updateApps package:[com.turbo.alarm], userId:[0], reason:[package is updated.].
2025-06-23 14:47:27.922  9486-9506  AlphabeticIndexCompat   com...le.android.apps.nexuslauncher  D  computeSectionName: cs: Turbo Alarm sectionName: T
2025-06-23 14:47:27.936  9486-9506  PackageUpdatedTask      com...le.android.apps.nexuslauncher  D  Package updated: mOp=UPDATE packages=[com.turbo.alarm], user=UserHandle{0}
2025-06-23 14:47:27.969  9486-9506  AlphabeticIndexCompat   com...le.android.apps.nexuslauncher  D  computeSectionName: cs: Turbo Alarm sectionName: T
2025-06-23 14:48:00.016  1370-1713  ActivityManager         system_server                        D  sync unfroze 30026 com.turbo.alarm:background for 10
2025-06-23 14:48:00.041  1370-4622  DatabaseUtils           system_server                        E  Writing exception to parcel (Ask Gemini)
                                                                                                    java.lang.SecurityException: com.turbo.alarm was not granted  this permission: android.permission.WRITE_SETTINGS.
                                                                                                    	at android.provider.Settings.isCallingPackageAllowedToPerformAppOpsProtectedOperation(Settings.java:22401)
                                                                                                    	at android.provider.Settings.checkAndNoteWriteSettingsOperation(Settings.java:22289)
                                                                                                    	at com.android.providers.settings.SettingsProvider.mutateSystemSetting(SettingsProvider.java:2081)
                                                                                                    	at com.android.providers.settings.SettingsProvider.insertSystemSetting(SettingsProvider.java:2034)
                                                                                                    	at com.android.providers.settings.SettingsProvider.call(SettingsProvider.java:510)
                                                                                                    	at android.content.ContentProvider.call(ContentProvider.java:2725)
                                                                                                    	at android.content.ContentProvider$Transport.call(ContentProvider.java:638)
                                                                                                    	at android.content.ContentProviderNative.onTransact(ContentProviderNative.java:307)
                                                                                                    	at android.os.Binder.execTransactInternal(Binder.java:1421)
                                                                                                    	at android.os.Binder.execTransact(Binder.java:1365)
2025-06-23 14:48:00.072  1370-4617  ActivityManager         system_server                        I  Background started FGS: Allowed [callingPackage: com.turbo.alarm; callingUid: 10312; uidState: RCVR; uidBFSL: n/a; intent: Intent { xflg=0x4 cmp=com.turbo.alarm/.services.AlarmRingingService (has extras) }; code:SYSTEM_ALLOW_LISTED; tempAllowListReason:<,reasonCode:SYSTEM_ALLOW_LISTED,duration:9223372036854775807,callingUid:-1>; allowWiu:-1; targetSdkVersion:34; callerTargetSdkVersion:34; startForegroundCount:0; bindFromPackage:null: isBindService:false]
2025-06-23 14:48:00.073 10059-10059 SensorReceiverBase      io.homeassistant.companion.android   D  Received intent: android.app.action.NEXT_ALARM_CLOCK_CHANGED
2025-06-23 14:48:00.105  1370-2867  ActivityManager         system_server                        W  Foreground service started from background can not have location/camera/microphone access: service com.turbo.alarm/.services.AlarmRingingService
2025-06-23 14:48:00.112  1370-2867  AS.HardeningEnforcer    system_server                        W  AudioHardening volume control for api 100 would be ignored for com.turbo.alarm (10312), level: full
2025-06-23 14:48:00.120 18582-18816 vol.Events              com.android.systemui                 I  writeEvent level_changed STREAM_ALARM 7
2025-06-23 14:48:00.145  9786-9786  AudioManager            com.turbo.alarm                      W  Use of stream types is deprecated for operations other than volume control
2025-06-23 14:48:00.145  9786-9786  AudioManager            com.turbo.alarm                      W  See the documentation of requestAudioFocus() for what to use instead with android.media.AudioAttributes to qualify your playback use case
2025-06-23 14:48:00.146  1370-1727  AS.HardeningEnforcer    system_server                        W  AudioHardening focus request for req 2 would be ignored for com.turbo.alarm (10312), android.media.AudioManager@442fe41, level: full
2025-06-23 14:48:00.146  1370-1727  MediaFocusControl       system_server                        I  requestAudioFocus() from uid/pid 10312/9786 AA=USAGE_ALARM/CONTENT_TYPE_SONIFICATION clientId=android.media.AudioManager@442fe41 callingPack=com.turbo.alarm req=2 flags=0x0 sdk=34
2025-06-23 14:48:00.163  9786-9804  TRuntime.C...ortBackend com.turbo.alarm                      I  Making request to: https://firebaselogging-pa.googleapis.com/v1/firelog/legacy/batchlog
2025-06-23 14:48:00.171  1370-2028  FlashNotifController    system_server                        D  alarm state changed: true
2025-06-23 14:48:00.171  1370-2028  FlashNotifController    system_server                        I  startFlashNotification: type=2, tag=alarm
2025-06-23 14:48:00.174 16193-25226 NearbyDiscovery         com.google.android.gms.persistent    I  [trigger-id=Plco7FD2204D, feature-id=Sass] FastPair: AudioEventListener find audio usage for alarm [CONTEXT service_id=265 ]
2025-06-23 14:48:00.303  1370-2271  AppOps                  system_server                        E  attributionTag  not declared in manifest of com.turbo.alarm
2025-06-23 14:48:00.324 16193-25268 NearbyDiscovery         com.google.android.gms.persistent    I  [trigger-id=Plco3ABD481E, feature-id=Sass] FastPair: AudioEventListener find audio usage for alarm [CONTEXT service_id=265 ]
2025-06-23 14:48:00.343 16193-25275 NearbyDiscovery         com.google.android.gms.persistent    I  [trigger-id=Plco97EE9901, feature-id=Sass] FastPair: AudioEventListener find audio usage for alarm [CONTEXT service_id=265 ]
2025-06-23 14:48:00.347  9486-9486  NotificationListener    com...le.android.apps.nexuslauncher  I  received notification removed event - com.turbo.alarm#UserHandle{0},category=-1
2025-06-23 14:48:00.387  4249-5435  NotificationPipeline    com.mobvoi.companion.at:persistent   I  Processing new notification: 0|com.turbo.alarm|2|null|10312
2025-06-23 14:48:00.403  1370-1727  AS.AudioService         system_server                        I  AudioHardening background playback would be muted for com.turbo.alarm (10312), level: full
2025-06-23 14:48:00.512 10059-21150 NextAlarmManager        io.homeassistant.companion.android   D  No alarm is scheduled, sending unavailable
2025-06-23 14:48:00.623 10059-10059 SensorReceiverBase      io.homeassistant.companion.android   D  Sensor(s) [volume_accessibility, volume_alarm, volume_call, volume_dtmf, volume_notification, volume_music, volume_ring, volume_system] corresponding to received event android.media.VOLUME_CHANGED_ACTION are disabled, skipping sensors update
2025-06-23 14:48:00.728 16193-16260 AlarmManager            com.google.android.gms.persistent    I  set [name: CloudSync type: 2 triggerAtMillis: 158203343 windowMillis: 158803343 intervalMillis: 0]
2025-06-23 14:48:00.729  1370-1727  AlarmManager            system_server                        W  Window length 158803343ms too long; limiting to 1 day
2025-06-23 14:48:00.925  9786-9786  JobService              com.turbo.alarm                      W  onNetworkChanged() not implemented in com.google.android.datatransport.runtime.scheduling.jobscheduling.JobInfoSchedulerService. Must override in a subclass.
2025-06-23 14:48:02.608  9786-9804  TRuntime.C...ortBackend com.turbo.alarm                      I  Status Code: 200
2025-06-23 14:48:03.642 16193-25282 AlarmManager            com.google.android.gms.persistent    I  setExactAndAllowWhileIdle [name: GCM_HB_ALARM type: 2 triggerAtMillis: 158376260]
2025-06-23 14:48:03.834  1370-2720  DatabaseUtils           system_server                        E  Writing exception to parcel (Ask Gemini)
                                                                                                    java.lang.SecurityException: com.turbo.alarm was not granted  this permission: android.permission.WRITE_SETTINGS.
                                                                                                    	at android.provider.Settings.isCallingPackageAllowedToPerformAppOpsProtectedOperation(Settings.java:22401)
                                                                                                    	at android.provider.Settings.checkAndNoteWriteSettingsOperation(Settings.java:22289)
                                                                                                    	at com.android.providers.settings.SettingsProvider.mutateSystemSetting(SettingsProvider.java:2081)
                                                                                                    	at com.android.providers.settings.SettingsProvider.insertSystemSetting(SettingsProvider.java:2034)
                                                                                                    	at com.android.providers.settings.SettingsProvider.call(SettingsProvider.java:510)
                                                                                                    	at android.content.ContentProvider.call(ContentProvider.java:2725)
                                                                                                    	at android.content.ContentProvider$Transport.call(ContentProvider.java:638)
                                                                                                    	at android.content.ContentProviderNative.onTransact(ContentProviderNative.java:307)
                                                                                                    	at android.os.Binder.execTransactInternal(Binder.java:1421)
                                                                                                    	at android.os.Binder.execTransact(Binder.java:1365)
2025-06-23 14:48:03.844  1370-4622  DatabaseUtils           system_server                        E  Writing exception to parcel (Ask Gemini)
                                                                                                    java.lang.SecurityException: com.turbo.alarm was not granted  this permission: android.permission.WRITE_SETTINGS.
                                                                                                    	at android.provider.Settings.isCallingPackageAllowedToPerformAppOpsProtectedOperation(Settings.java:22401)
                                                                                                    	at android.provider.Settings.checkAndNoteWriteSettingsOperation(Settings.java:22289)
                                                                                                    	at com.android.providers.settings.SettingsProvider.mutateSystemSetting(SettingsProvider.java:2081)
                                                                                                    	at com.android.providers.settings.SettingsProvider.insertSystemSetting(SettingsProvider.java:2034)
                                                                                                    	at com.android.providers.settings.SettingsProvider.call(SettingsProvider.java:510)
                                                                                                    	at android.content.ContentProvider.call(ContentProvider.java:2725)
                                                                                                    	at android.content.ContentProvider$Transport.call(ContentProvider.java:638)
                                                                                                    	at android.content.ContentProviderNative.onTransact(ContentProviderNative.java:307)
                                                                                                    	at android.os.Binder.execTransactInternal(Binder.java:1421)
                                                                                                    	at android.os.Binder.execTransact(Binder.java:1365)
2025-06-23 14:48:03.876 16193-25268 NearbyDiscovery         com.google.android.gms.persistent    I  [trigger-id=Plco8354D308, feature-id=Sass] FastPair: AudioEventListener find audio usage for alarm [CONTEXT service_id=265 ]
2025-06-23 14:48:03.889  9786-9853  JobInfo                 com.turbo.alarm                      W  Requested important-while-foreground flag for job4426 is ignored and takes no effect
2025-06-23 14:48:03.899  1370-3990  ActivityManager         system_server                        W  Foreground service started from background can not have location/camera/microphone access: service com.turbo.alarm/.services.AlarmRingingService
2025-06-23 14:48:03.901  1370-2867  Foreground...ggerModule system_server                        W  Logger should be tracking FGS types correctly for UID 10312 in package com.turbo.alarm
2025-06-23 14:48:03.911  1370-2028  FlashNotifController    system_server                        D  alarm state changed: false
2025-06-23 14:48:03.911  1370-2028  FlashNotifController    system_server                        I  stopFlashNotification: tag=alarm
2025-06-23 14:48:03.919  1370-2867  AppOps                  system_server                        E  attributionTag  not declared in manifest of com.turbo.alarm
2025-06-23 14:48:03.935  9786-9786  MediaPlayer             com.turbo.alarm                      V  resetDrmState:  mDrmInfo=null mDrmProvisioningThread=null mPrepareDrmInProgress=false mActiveDrmScheme=false
2025-06-23 14:48:03.935  9486-9486  NotificationListener    com...le.android.apps.nexuslauncher  I  received notification removed event - com.turbo.alarm#UserHandle{0},category=-1
2025-06-23 14:48:03.935  9786-9786  MediaPlayer             com.turbo.alarm                      V  cleanDrmObj: mDrmObj=null mDrmSessionId=null
2025-06-23 14:48:03.936  1370-2588  AS.HardeningEnforcer    system_server                        W  AudioHardening volume control for api 100 would be ignored for com.turbo.alarm (10312), level: full
2025-06-23 14:48:03.936  4249-5435  NotificationPipeline    com.mobvoi.companion.at:persistent   I  Processing notification dismissal on phone: 0|com.turbo.alarm|2|null|10312
2025-06-23 14:48:03.941  1370-2588  MediaFocusControl       system_server                        I  abandonAudioFocus() from uid/pid 10312/9786 clientId=android.media.AudioManager@442fe41 callingPack=com.turbo.alarm
2025-06-23 14:48:03.951 18582-18816 vol.Events              com.android.systemui                 I  writeEvent level_changed STREAM_ALARM 2
2025-06-23 14:48:03.973  4249-5435  NotifDismissalHandler   com.mobvoi.companion.at:persistent   I  Notification 0|com.turbo.alarm|2|null|10312 dismissed on phone, deleting data item /bridger/stream_item:fea05180:com.turbo.alarm:2:null_tag:0~7Ccom.turbo.alarm~7C2~7Cnull~7C10312
2025-06-23 14:48:04.012  1370-2290  NotificationService     system_server                        I  cancelToast pkg=com.turbo.alarm token=android.os.BinderProxy@2bcba18
2025-06-23 14:48:04.013  1370-2290  NotificationService     system_server                        W  Toast already cancelled. pkg=com.turbo.alarm token=android.os.BinderProxy@2bcba18
2025-06-23 14:48:04.029  9786-9786  MediaPlayer             com.turbo.alarm                      V  resetDrmState:  mDrmInfo=null mDrmProvisioningThread=null mPrepareDrmInProgress=false mActiveDrmScheme=false
2025-06-23 14:48:04.029  9786-9786  MediaPlayer             com.turbo.alarm                      V  cleanDrmObj: mDrmObj=null mDrmSessionId=null
2025-06-23 14:48:04.030  1370-1727  AS.HardeningEnforcer    system_server                        W  AudioHardening volume control for api 100 would be ignored for com.turbo.alarm (10312), level: full
2025-06-23 14:48:04.035  1370-2588  MediaFocusControl       system_server                        I  abandonAudioFocus() from uid/pid 10312/9786 clientId=android.media.AudioManager@442fe41 callingPack=com.turbo.alarm
2025-06-23 14:48:04.035  9786-9786  MediaPlayer             com.turbo.alarm                      V  resetDrmState:  mDrmInfo=null mDrmProvisioningThread=null mPrepareDrmInProgress=false mActiveDrmScheme=false
2025-06-23 14:48:04.035  9786-9786  MediaPlayer             com.turbo.alarm                      V  cleanDrmObj: mDrmObj=null mDrmSessionId=null
2025-06-23 14:48:04.046  9786-9855  WM-WorkerWrapper        com.turbo.alarm                      I  Worker result SUCCESS for Work [ id=ed80e618-b51e-4578-beee-36267d7d6468, tags={ com.turbo.alarm.glance.UpdateAppWidgetWorker } ]
2025-06-23 14:48:04.068  1370-2271  JobScheduler            system_server                        W  Job didn't exist in JobStore: 6788b00 #u0a312/4426 com.turbo.alarm/androidx.work.impl.background.systemjob.SystemJobService
2025-06-23 14:48:04.114  1370-1370  NotificationService     system_server                        I  Cannot find enqueued record for key: 0|com.turbo.alarm|2|null|10312
2025-06-23 14:48:04.445 10059-10059 SensorReceiverBase      io.homeassistant.companion.android   D  Sensor(s) [volume_accessibility, volume_alarm, volume_call, volume_dtmf, volume_notification, volume_music, volume_ring, volume_system] corresponding to received event android.media.VOLUME_CHANGED_ACTION are disabled, skipping sensors update
2025-06-23 14:48:04.887  1370-1755  AppsFilter              system_server                        I  interaction: PackageSetting{2497229 com.neolabs.alarmsync.phone/10426} -> PackageSetting{1088506 com.turbo.alarm/10312} BLOCKED
2025-06-23 14:48:04.887  1370-1755  AppsFilter              system_server                        I  interaction: PackageSetting{6f66b86 com.google.android.microdroid.empty_payload/10263} -> PackageSetting{1088506 com.turbo.alarm/10312} BLOCKED
2025-06-23 14:48:04.904  1370-2125  ShortcutService         system_server                        D  changing package: com.turbo.alarm userId=0
2025-06-23 14:48:05.035  3867-24712 AiAiEcho                com.google.android.as                I  AppIndexer Package:[com.turbo.alarm] UserProfile:[0] Enabled:[true].
2025-06-23 14:48:05.035  3867-24712 AiAiEcho                com.google.android.as                I  AppFetcherImplV2 updateApps package:[com.turbo.alarm], userId:[0], reason:[package is updated.].
2025-06-23 14:48:05.094  9486-9506  AlphabeticIndexCompat   com...le.android.apps.nexuslauncher  D  computeSectionName: cs: Turbo Alarm sectionName: T
2025-06-23 14:48:05.104  9486-9506  PackageUpdatedTask      com...le.android.apps.nexuslauncher  D  Package updated: mOp=UPDATE packages=[com.turbo.alarm], user=UserHandle{0}
2025-06-23 14:48:05.142  9486-9506  AlphabeticIndexCompat   com...le.android.apps.nexuslauncher  D  computeSectionName: cs: Turbo Alarm sectionName: T
2025-06-23 14:48:08.136  1370-2589  NotificationService     system_server                        W  Toast already killed. pkg=com.turbo.alarm token=android.os.BinderProxy@4ffb710



here is the logcat entry for the watch for the same duration of time period

# WearAlarmSync

A companion app for Android and Wear OS that restores the ability to snooze or dismiss phone alarms directly from your Wear OS watch.

## Features

- Detects alarm notifications on your phone
- Instantly syncs alarm state to your Wear OS watch
- Allows snooze/dismiss actions from the watch
- Closes the alarm screen on the watch if the alarm is handled on the phone
- Modern, Material-inspired UI

## How it works

1. **Phone app** listens for alarm notifications and sends sync messages to the watch.
2. **Wear app** displays an alarm screen with snooze/dismiss controls.
3. Actions on either device are reflected on the other for a seamless experience.

## Installation

### Prerequisites

- Android phone (API 31+)
- Wear OS watch (API 31+)

### Steps

1. Download and install the APKs for both phone and watch from the [Releases](https://github.com/nishant713/WearAlarmSync/releases) page.
2. Grant notification access to the phone app.
3. Pair your Wear OS watch with your phone.
4. Launch the app on both devices.

## Permissions

- **Notification Access:** Required to detect alarm notifications.
- **Wake Lock:** Keeps the screen on during alarms.

## Security & Privacy

- No data is sent to any server.
- All communication is local between your phone and watch.

## Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.