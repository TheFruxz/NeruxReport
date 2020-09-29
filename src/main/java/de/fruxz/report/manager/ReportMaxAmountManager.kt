package de.fruxz.report.manager

import de.fruxz.report.NeruxReport

class ReportMaxAmountManager {

    val controller = NeruxReport.configFile
    val path = "maxActiveReportAmount"

    fun getMaxActiveReportAmount(): Int {
        controller.load()
        val content = controller.get<Int?>(path)

        return if (content != null) {
            content
        } else {
            controller.set(path, 20)
            20
        }

    }

    fun setMaxActiveReportAmount(v: Int) {
        controller.load()
        controller.set(path, v)
        controller.save()
    }

}