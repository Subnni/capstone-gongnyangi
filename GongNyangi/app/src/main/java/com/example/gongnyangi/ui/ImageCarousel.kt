package com.example.gongnyangi.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@Composable
fun ImageCarousel(
    itemCount: Int, //카드 개수
    onPageChanged: (Int) -> Unit //콜백
){
    //몇 개의 카드를 보여줄 지 결정
    val pagerState = rememberPagerState(pageCount = {itemCount})

    // 특정 값이 변할 때 코드 실행
    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    Box( modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 80.dp), //양 옆 카드
            pageSpacing = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            page ->
            //개별 카드 디자인
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .graphicsLayer(){
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                                ).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // 투명도 조절
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ){
                //카드 내부 내용
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "이미지 ${page + 1}",
                        color = Color.Black
                    )
                }
            }

        }
    }
}