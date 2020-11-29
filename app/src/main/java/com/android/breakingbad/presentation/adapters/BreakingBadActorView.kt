package com.android.breakingbad.presentation.adapters

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.android.breakingbad.R
import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.Completable
import kotlinx.android.synthetic.main.actor_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class BreakingBadActorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {
    private var rootLayout: View? = null
    lateinit var breakbadCharacterRoomItem: BreakbadCharacterRoomItem

    init {
        rootLayout =
            LayoutInflater.from(context).inflate(R.layout.actor_view, this, true)

    }

    fun setBreakingBadData(breakbadCharacterRoomItem: BreakbadCharacterRoomItem) {
        this.breakbadCharacterRoomItem = breakbadCharacterRoomItem
        bindDataToView()
    }

    private fun bindDataToView() {

        Glide.with(context)
            .load(breakbadCharacterRoomItem?.img)
            .into(media_image)
        primary_text?.text = breakbadCharacterRoomItem?.name
        generateDetailText(breakbadCharacterRoomItem!!, supporting_text).subscribe()
        sub_text?.text = "Recorded date of birth ${breakbadCharacterRoomItem?.birthday}"
        expand_button.clicks().subscribe {
            if (supporting_text.visibility == View.VISIBLE) {
                expand_button.setImageResource(R.drawable.ic_expand)
                supporting_text.visibility = View.GONE
            } else {
                expand_button.setImageResource(R.drawable.ic_expand_less)
                supporting_text.visibility = View.VISIBLE
            }
        }

    }

    private fun generateDetailText(
        breakbadCharacterRoomItem: BreakbadCharacterRoomItem,
        text: TextView
    ): Completable {
        var detailText = ""
        detailText =
            "The character ${breakbadCharacterRoomItem.name} is portrayed by ${breakbadCharacterRoomItem.portrayed} in the ${breakbadCharacterRoomItem.category} show.  "
        detailText = detailText.plus(generateBirthdayText(breakbadCharacterRoomItem))
        detailText =
            detailText.plus(" and in the show they go by the nickname ${breakbadCharacterRoomItem.nickname}.")
        detailText = detailText.plus(
            " In the show ${breakbadCharacterRoomItem.name} works as ${
                breakbadCharacterRoomItem.occupation?.let {
                    generateOccupationText(
                        it
                    )
                }
            }")
        detailText = detailText.plus(
            "${
                breakbadCharacterRoomItem.status?.let {
                    generateStatus(
                        it
                    )
                }
            }."
        )

        detailText = detailText.plus("Their character appeared in series ${
            breakbadCharacterRoomItem.appearance?.let {
                generateTextForAppearedInSeries(
                    it
                )
            }
        }."
        )
        text.text = detailText
        return Completable.complete()
    }

    private fun generateOccupationText(occupation: List<String>): String {
        if (!occupation.isNullOrEmpty()) {
            var text = occupation.joinToString(
                separator = ", ",
                prefix = " ",
                postfix = " ",
                limit = 3,
                truncated = "."
            )
            return text
        }

        return ""
    }

    private fun generateStatus(status: String): String {
        if (status.contains("Alive")) {
            return " and is presumed to be still alive"
        } else {
            return "is ${status.toLowerCase()}."
        }
    }

    private fun generateTextForAppearedInSeries(appearance: List<Int>): String {
        if (!appearance.isNullOrEmpty()) {
            return appearance.joinToString(
                separator = ", ",
                prefix = " ",
                postfix = " ",
                limit = 5,
                truncated = "."
            )
        }
        return ""
    }

    private fun generateBirthdayText(breakbadCharacterRoomItem: BreakbadCharacterRoomItem): String {
        var birthdayText: String? = null
        birthdayText = breakbadCharacterRoomItem?.birthday?.toLowerCase()
        if (birthdayText?.contains("unknown")!!) {
            return "We don't know when ${breakbadCharacterRoomItem.name} was born"
        } else {
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val parsedDate: Date = formatter.parse(breakbadCharacterRoomItem.birthday)
            val dob = SimpleDateFormat("EEEE dd-MMM-yyyy ").format(parsedDate)

            return "${breakbadCharacterRoomItem.name}  was born on ${
                dob.format(breakbadCharacterRoomItem.birthday)
            }"
        }
    }

}