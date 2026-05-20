/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.ACSlit.pro.tooling.impl.progress

import com.ACSlit.pro.tooling.events.StatusEvent
import com.ACSlit.pro.tooling.events.configuration.ProjectConfigurationOperationResult.PluginApplicationResult
import com.ACSlit.pro.tooling.events.download.FileDownloadFinishEvent
import com.ACSlit.pro.tooling.events.download.FileDownloadProgressEvent
import com.ACSlit.pro.tooling.events.download.FileDownloadStartEvent
import com.ACSlit.pro.tooling.events.internal.DefaultFinishEvent
import com.ACSlit.pro.tooling.events.internal.DefaultOperationDescriptor
import com.ACSlit.pro.tooling.events.internal.DefaultOperationResult
import com.ACSlit.pro.tooling.events.internal.DefaultProgressEvent
import com.ACSlit.pro.tooling.events.internal.DefaultStartEvent
import com.ACSlit.pro.tooling.events.task.TaskFinishEvent
import com.ACSlit.pro.tooling.events.task.TaskProgressEvent
import com.ACSlit.pro.tooling.events.task.TaskStartEvent
import com.ACSlit.pro.tooling.events.transform.TransformFinishEvent
import com.ACSlit.pro.tooling.events.transform.TransformOperationDescriptor.SubjectDescriptor
import com.ACSlit.pro.tooling.events.transform.TransformOperationResult
import com.ACSlit.pro.tooling.events.transform.TransformStartEvent
import com.ACSlit.pro.tooling.events.work.WorkItemFinishEvent
import com.ACSlit.pro.tooling.events.work.WorkItemOperationResult
import com.ACSlit.pro.tooling.events.work.WorkItemProgressEvent
import com.ACSlit.pro.tooling.events.work.WorkItemStartEvent
import com.ACSlit.pro.tooling.model.PluginIdentifier
import org.gradle.tooling.events.OperationDescriptor
import org.gradle.tooling.events.ProgressEvent
import org.gradle.tooling.events.configuration.ProjectConfigurationFinishEvent
import org.gradle.tooling.events.configuration.ProjectConfigurationOperationDescriptor
import org.gradle.tooling.events.configuration.ProjectConfigurationOperationResult
import org.gradle.tooling.events.configuration.ProjectConfigurationProgressEvent
import org.gradle.tooling.events.configuration.ProjectConfigurationStartEvent
import org.gradle.tooling.events.configuration.ProjectConfigurationSuccessResult
import org.gradle.tooling.events.download.FileDownloadOperationDescriptor
import org.gradle.tooling.events.download.FileDownloadResult
import org.gradle.tooling.events.task.TaskExecutionResult
import org.gradle.tooling.events.task.TaskFailureResult
import org.gradle.tooling.events.task.TaskOperationDescriptor
import org.gradle.tooling.events.task.TaskOperationResult
import org.gradle.tooling.events.task.TaskSkippedResult
import org.gradle.tooling.events.task.TaskSuccessResult
import org.gradle.tooling.events.transform.TransformOperationDescriptor
import org.gradle.tooling.events.transform.TransformSuccessResult
import org.gradle.tooling.events.work.WorkItemOperationDescriptor
import org.gradle.tooling.events.work.WorkItemSuccessResult
import org.gradle.tooling.model.ProjectIdentifier

/** @author Akash Yadav */
class EventTransformer {
  companion object {

    // ------------------------ COMMON -------------------------
    private fun operationDescriptor(
        descriptor: OperationDescriptor?
    ): com.ACSlit.pro.tooling.events.OperationDescriptor? =
        when (descriptor) {
          null -> null
          is ProjectConfigurationOperationDescriptor -> projectConfigurationDescriptor(descriptor)
          is FileDownloadOperationDescriptor -> fileDownloadDescriptor(descriptor)
          is TaskOperationDescriptor -> taskDescriptor(descriptor)
          is TransformOperationDescriptor -> transformDescriptor(descriptor)
          is WorkItemOperationDescriptor -> workDescriptor(descriptor)
          else ->
              DefaultOperationDescriptor(
                  name = descriptor.name,
                  displayName = descriptor.displayName,
              )
        }

    // ----------------- PROJECT CONFIGURATION --------------------
    @JvmStatic
    fun projectConfigurationStart(
        event: ProjectConfigurationStartEvent
    ): com.ACSlit.pro.tooling.events.configuration.ProjectConfigurationProgressEvent =
        com.ACSlit.pro.tooling.events.configuration.ProjectConfigurationStartEvent(
            displayName = event.displayName,
            eventTime = event.eventTime,
            descriptor = projectConfigurationDescriptor(event.descriptor),
        )

    @JvmStatic
    fun projectConfigurationProgress(
        event: ProjectConfigurationProgressEvent
    ): com.ACSlit.pro.tooling.events.configuration.ProjectConfigurationProgressEvent =
        com.ACSlit.pro.tooling.events.configuration.ProjectConfigurationProgressEvent(
            displayName = event.displayName,
            eventTime = event.eventTime,
            descriptor = projectConfigurationDescriptor(event.descriptor),
        )

    @JvmStatic
    fun projectConfigurationFinish(
        event: ProjectConfigurationFinishEvent
    ): com.ACSlit.pro.tooling.events.configuration.ProjectConfigurationProgressEvent =
        com.ACSlit.pro.tooling.events.configuration.ProjectConfigurationFinishEvent(
            displayName = event.displayName,
            eventTime = event.eventTime,
            descriptor = projectConfigurationDescriptor(event.descriptor),
            result = projectConfigurationResult(event.result),
        )

    private fun projectConfigurationResult(
        result: ProjectConfigurationOperationResult
    ): com.ACSlit.pro.tooling.events.configuration.ProjectConfigurationOperationResult =
        com.ACSlit.pro.tooling.events.configuration.ProjectConfigurationOperationResult(
            pluginApplicationResults =
                result.pluginApplicationResults.map {
                  PluginApplicationResult(
                      plugin = PluginIdentifier(it.plugin?.displayName ?: "Unknown plugin"),
                      it.totalConfigurationTime.toMillis(),
                  )
                },
            startTime = result.startTime,
            endTime = result.endTime,
            success = result is ProjectConfigurationSuccessResult,
        )

    private fun projectConfigurationDescriptor(
        descriptor: ProjectConfigurationOperationDescriptor
    ): com.ACSlit.pro.tooling.events.configuration.ProjectConfigurationOperationDescriptor =
        com.ACSlit.pro.tooling.events.configuration.ProjectConfigurationOperationDescriptor(
            project = projectIdentifier(descriptor.project),
            name = descriptor.name,
            displayName = descriptor.displayName,
        )

    private fun projectIdentifier(
        project: ProjectIdentifier
    ): com.ACSlit.pro.tooling.model.ProjectIdentifier =
        com.ACSlit.pro.tooling.model.ProjectIdentifier(
            projectPath = project.projectPath,
            buildIdentifier =
                com.ACSlit.pro.tooling.model.BuildIdentifier(project.buildIdentifier.rootDir),
        )

    // ---------------------- FILE DOWNLOAD ---------------------------------
    @JvmStatic
    fun fileDownloadStart(
        event: org.gradle.tooling.events.download.FileDownloadStartEvent
    ): FileDownloadStartEvent =
        FileDownloadStartEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = fileDownloadDescriptor(event.descriptor),
        )

    @JvmStatic
    fun fileDownloadProgress(
        event: org.gradle.tooling.events.download.FileDownloadProgressEvent
    ): FileDownloadProgressEvent =
        FileDownloadProgressEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = fileDownloadDescriptor(event.descriptor),
        )

    @JvmStatic
    fun fileDownloadFinish(
        event: org.gradle.tooling.events.download.FileDownloadFinishEvent
    ): FileDownloadFinishEvent =
        FileDownloadFinishEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = fileDownloadDescriptor(event.descriptor),
            result = fileDownloadResult(event.result),
        )

    private fun fileDownloadResult(
        result: FileDownloadResult
    ): com.ACSlit.pro.tooling.events.download.FileDownloadResult =
        com.ACSlit.pro.tooling.events.download.FileDownloadResult(
            bytesDownloaded = result.bytesDownloaded,
            startTime = result.startTime,
            endTime = result.endTime,
        )

    private fun fileDownloadDescriptor(
        descriptor: FileDownloadOperationDescriptor
    ): com.ACSlit.pro.tooling.events.download.FileDownloadOperationDescriptor =
        com.ACSlit.pro.tooling.events.download.FileDownloadOperationDescriptor(
            descriptor.uri,
            descriptor.name,
            descriptor.displayName,
        )

    // -------------------- TASK -------------------------------
    @JvmStatic
    fun taskStart(event: org.gradle.tooling.events.task.TaskStartEvent): TaskStartEvent =
        TaskStartEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = taskDescriptor(event.descriptor),
        )

    @JvmStatic
    fun taskProgress(event: org.gradle.tooling.events.task.TaskProgressEvent): TaskProgressEvent =
        TaskProgressEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = taskDescriptor(event.descriptor),
        )

    @JvmStatic
    fun taskFinish(event: org.gradle.tooling.events.task.TaskFinishEvent): TaskFinishEvent =
        TaskFinishEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = taskDescriptor(event.descriptor),
            result = taskResult(event.result),
        )

    private fun taskResult(
        result: TaskOperationResult
    ): com.ACSlit.pro.tooling.events.task.TaskOperationResult {

      // The order of conditions must not change here.

      if (result is TaskSuccessResult) {
        return com.ACSlit.pro.tooling.events.task.TaskSuccessResult(
            result.isUpToDate,
            result.isFromCache,
            result.startTime,
            result.endTime,
            result.isIncremental,
            result.executionReasons,
        )
      }

      if (result is TaskFailureResult) {
        return com.ACSlit.pro.tooling.events.task.TaskFailureResult(
            result.startTime,
            result.endTime,
        )
      }

      if (result is TaskExecutionResult) {
        return com.ACSlit.pro.tooling.events.task.TaskExecutionResult(
            result.startTime,
            result.endTime,
            result.isIncremental,
            result.executionReasons,
        )
      }

      if (result is TaskSkippedResult) {
        return com.ACSlit.pro.tooling.events.task.TaskSkippedResult(
            result.skipMessage,
            result.startTime,
            result.endTime,
        )
      }

      return com.ACSlit.pro.tooling.events.task.TaskOperationResult(
          startTime = result.startTime,
          endTime = result.endTime,
      )
    }

    private fun taskDescriptor(
        descriptor: TaskOperationDescriptor
    ): com.ACSlit.pro.tooling.events.task.TaskOperationDescriptor =
        com.ACSlit.pro.tooling.events.task.TaskOperationDescriptor(
            dependencies =
                descriptor.dependencies
                    .filterNotNull()
                    .mapNotNull { operationDescriptor(it) }
                    .toSet(),
            originPlugin =
                PluginIdentifier(descriptor.originPlugin?.displayName ?: "Unknown plugin"),
            taskPath = descriptor.taskPath,
            name = descriptor.name,
            displayName = descriptor.displayName,
        )

    // ----------------------- TRANSFORM -------------------------
    @JvmStatic
    fun transformStart(
        event: org.gradle.tooling.events.transform.TransformStartEvent
    ): TransformStartEvent =
        TransformStartEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = transformDescriptor(event.descriptor),
        )

    @JvmStatic
    fun transformProgress(
        event: org.gradle.tooling.events.transform.TransformProgressEvent
    ): TransformStartEvent =
        TransformStartEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = transformDescriptor(event.descriptor),
        )

    @JvmStatic
    fun transformFinish(
        event: org.gradle.tooling.events.transform.TransformFinishEvent
    ): TransformFinishEvent =
        TransformFinishEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            operationDescriptor = transformDescriptor(event.descriptor),
            result = transformResult(event.result),
        )

    private fun transformResult(
        result: org.gradle.tooling.events.transform.TransformOperationResult
    ): TransformOperationResult =
        TransformOperationResult(
            success = result is TransformSuccessResult,
            startTime = result.startTime,
            endTime = result.endTime,
        )

    private fun transformDescriptor(
        descriptor: TransformOperationDescriptor
    ): com.ACSlit.pro.tooling.events.transform.TransformOperationDescriptor =
        com.ACSlit.pro.tooling.events.transform.TransformOperationDescriptor(
            name = descriptor.name,
            displayName = descriptor.displayName,
            subject = SubjectDescriptor(descriptor.subject.displayName),
            transformer =
                com.ACSlit.pro.tooling.events.transform.TransformOperationDescriptor
                    .TransformerDescriptor(descriptor.transformer.displayName),
            dependencies = descriptor.dependencies.mapNotNull { operationDescriptor(it) }.toSet(),
        )

    // ----------------------- WORK ITEM -------------------------
    @JvmStatic
    fun workStart(event: org.gradle.tooling.events.work.WorkItemStartEvent): WorkItemStartEvent =
        WorkItemStartEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = workDescriptor(event.descriptor),
        )

    @JvmStatic
    fun workProgress(
        event: org.gradle.tooling.events.work.WorkItemProgressEvent
    ): WorkItemProgressEvent =
        WorkItemProgressEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = workDescriptor(event.descriptor),
        )

    @JvmStatic
    fun workFinish(event: org.gradle.tooling.events.work.WorkItemFinishEvent): WorkItemFinishEvent =
        WorkItemFinishEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            operationDescriptor = workDescriptor(event.descriptor),
            result = workResult(event.result),
        )

    private fun workResult(
        result: org.gradle.tooling.events.work.WorkItemOperationResult
    ): WorkItemOperationResult =
        WorkItemOperationResult(
            success = result is WorkItemSuccessResult,
            startTime = result.startTime,
            endTime = result.endTime,
        )

    private fun workDescriptor(
        descriptor: WorkItemOperationDescriptor
    ): com.ACSlit.pro.tooling.events.work.WorkItemOperationDescriptor =
        com.ACSlit.pro.tooling.events.work.WorkItemOperationDescriptor(
            name = descriptor.name,
            displayName = descriptor.displayName,
            className = descriptor.className,
        )

    // ---------------------------- STATUS ---------------------------------
    fun statusEvent(event: org.gradle.tooling.events.StatusEvent): StatusEvent =
        StatusEvent(
            total = event.total,
            progress = event.progress,
            unit = event.unit,
            displayName = event.displayName,
            eventTime = event.eventTime,
            descriptor = operationDescriptor(event.descriptor)!!,
        )

    // ----------------------- DEFAULT ----------------------------------
    fun progress(event: ProgressEvent): com.ACSlit.pro.tooling.events.ProgressEvent =
        DefaultProgressEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = operationDescriptor(event.descriptor)!!,
        )

    fun start(
        event: org.gradle.tooling.events.StartEvent
    ): com.ACSlit.pro.tooling.events.ProgressEvent =
        DefaultStartEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = operationDescriptor(event.descriptor)!!,
        )

    fun finish(
        event: org.gradle.tooling.events.FinishEvent
    ): com.ACSlit.pro.tooling.events.ProgressEvent =
        DefaultFinishEvent(
            eventTime = event.eventTime,
            displayName = event.displayName,
            descriptor = operationDescriptor(event.descriptor)!!,
            result = DefaultOperationResult(event.result.startTime, event.result.endTime),
        )
  }
}
