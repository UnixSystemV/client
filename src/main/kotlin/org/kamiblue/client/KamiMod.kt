package org.kamiblue.client

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.kamiblue.client.event.ForgeEventProcessor
import org.kamiblue.client.gui.mc.KamiGuiUpdateNotification
import org.kamiblue.client.util.ConfigUtils
import org.kamiblue.client.util.threads.BackgroundScope
import java.io.File

@Mod(
    modid = KamiMod.ID,
    name = KamiMod.NAME,
    version = KamiMod.VERSION
)
class KamiMod {

    companion object {
        const val NAME = "FurryWareBlue"
        const val ID = "furrywareblue"
        const val DIRECTORY = "FurryWareBlue/"

        const val VERSION = "B2" // Used for debugging. R.MM.DD-hash format.
        const val VERSION_SIMPLE = "B2" // Shown to the user. R.MM.DD[-beta] format.
        const val VERSION_MAJOR = "B2" // Used for update checking. RR.MM.01 format.
        const val BUILD_NUMBER = -1 // Do not remove, currently unused but will be used in the future.

        const val APP_ID = "898215072340533248"

        const val DOWNLOADS_API = ""
        const val CAPES_JSON = "https://github.com/UnixSystemV/FurryWareBlue-Capes/raw/main/capes.json"
        const val GITHUB_LINK = "https://google.com"
        const val WEBSITE_LINK = "https://google.com"

        const val KAMI_KATAKANA = "毛皮のような波"

        val LOG: Logger = LogManager.getLogger(NAME)

        var ready: Boolean = false; private set
    }

    @Suppress("UNUSED_PARAMETER")
    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        val directory = File(DIRECTORY)
        if (!directory.exists()) directory.mkdir()

        KamiGuiUpdateNotification.updateCheck()
        LoaderWrapper.preLoadAll()
    }

    @Suppress("UNUSED_PARAMETER")
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        LOG.info("Initializing $NAME $VERSION")

        LoaderWrapper.loadAll()

        MinecraftForge.EVENT_BUS.register(ForgeEventProcessor)

        ConfigUtils.moveAllLegacyConfigs()
        ConfigUtils.loadAll()

        BackgroundScope.start()

        LOG.info("$NAME initialized!")
    }

    @Suppress("UNUSED_PARAMETER")
    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        ready = true
    }
}