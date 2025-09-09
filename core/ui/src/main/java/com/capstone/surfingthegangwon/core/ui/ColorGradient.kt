package com.capstone.surfingthegangwon.core.ui

import android.graphics.LinearGradient
import android.graphics.Shader
import android.widget.TextView

class ColorGradient {

    companion object {
        /**
         * TextView의 텍스트 색상을 지정한 색상 배열을 이용해
         * 선형 그라데이션(LinearGradient) 효과로 변경하는 확장 함수입니다.
         */
        fun TextView.setTextColorAsLinearGradient(colors: Array<Int>) {
            // 색상 배열이 비어 있으면 처리하지 않고 종료
            if (colors.isEmpty()) {
                return
            }

            // 기본 텍스트 색상을 첫 번째 색상으로 설정
            setTextColor(colors[0])

            // 텍스트에 그라데이션 효과를 적용하는 LinearGradient 생성
            this.paint.shader = LinearGradient(
                0f, // 그라데이션 시작 X 좌표 (텍스트 시작점)
                0f, // 그라데이션 시작 Y 좌표 (텍스트 위쪽)
                paint.measureText(this.text.toString()), // 그라데이션 끝 X 좌표 (텍스트 길이만큼)
                this.textSize, // 그라데이션 끝 Y 좌표 (텍스트 크기만큼)
                colors.toIntArray(), // 그라데이션에 사용할 색상 배열 (IntArray 형태)
                arrayOf(0f, 1f).toFloatArray(), // 색상 위치 배열 (0%에서 100%까지)
                Shader.TileMode.CLAMP // 경계 밖의 영역은 가장자리 색상으로 고정
            )
        }
    }

}