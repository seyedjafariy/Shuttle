package com.seyedjafariy.shared.repository

import com.seyedjafariy.shared.model.LaunchesLoadSpec
import com.seyedjafariy.shared.model.dto.QueryOptionDTO
import com.seyedjafariy.shared.model.dto.QueryPopulateDTO
import com.seyedjafariy.shared.model.dto.QueryRequestDTO
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class LaunchQueryDTOFactory {

    fun createQueryDTO(spec: LaunchesLoadSpec) = QueryRequestDTO(
        options = QueryOptionDTO(
            pagination = false,
            select = LAUNCH_NEEDED_PARAMS,
            sort = spec.sorting.toYearSortDTO(),
            populate = listOf(
                QueryPopulateDTO(
                    LAUNCH_POPULATE_PATH_ROCKET,
                    ROCKET_NEEDED_PARAMS
                )
            ),
        ),
        queryMap = createQuery(spec)
    )

    private fun LaunchesLoadSpec.Sort.toYearSortDTO() =
        when (this) {
            LaunchesLoadSpec.Sort.ASC -> SORTING_ASC + LAUNCH_SORTING_PARAM
            LaunchesLoadSpec.Sort.DESC -> SORTING_DESC + LAUNCH_SORTING_PARAM
        }

    private fun createQuery(spec: LaunchesLoadSpec): Map<String, JsonElement> {
        if (spec.launchYear == null && spec.operationStatus == LaunchesLoadSpec.OperationStatus.All) {
            return emptyMap()
        }

        return buildMap {
            if (spec.operationStatus != LaunchesLoadSpec.OperationStatus.All) {
                put(LAUNCH_OPERATION_PARAM_SUCCESS, JsonPrimitive(spec.operationStatus.toDTO()))
            }
            if (spec.launchYear != null) {

                put(
                    LAUNCH_OPERATION_PARAM_DATE_UTC, JsonObject(
                        mapOf(
                            "\$gte" to JsonPrimitive(spec.launchYear.year.toString()),
                            "\$lte" to JsonPrimitive((spec.launchYear.year + 1).toString()),
                        )
                    )
                )
            }
        }
    }

    private fun LaunchesLoadSpec.OperationStatus.toDTO() = when (this) {
        LaunchesLoadSpec.OperationStatus.All -> error("All is not supported")
        LaunchesLoadSpec.OperationStatus.Success -> true
        LaunchesLoadSpec.OperationStatus.Failed -> false
    }


}

private const val SORTING_ASC = ""
private const val SORTING_DESC = "-"
private const val LAUNCH_OPERATION_PARAM_SUCCESS = "success"
private const val LAUNCH_OPERATION_PARAM_DATE_UTC = "date_utc"

private const val LAUNCH_SORTING_PARAM = "date_utc"

private const val LAUNCH_POPULATE_PATH_ROCKET = "rocket"

private val ROCKET_NEEDED_PARAMS = listOf("name", "type", "id")
private val LAUNCH_NEEDED_PARAMS =
    listOf("name", "upcoming", "date_utc", "links.patch", "links.wikipedia", "links.article", "links.webcast", "success", "rocket")