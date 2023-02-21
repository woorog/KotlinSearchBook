# KotlinSearchBook

인터파크 api를 이용한 책검색입니다.

주요기능으로는
retrofit2 2.9.0 버전
room 2.5.0 버전

api는 33이상 을 사용할수 있게 만들었습니다.
그리고 현재 intent의 getParcelableExtra 부분이 업데이트되면서

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }
  부분을 추가해야 오류없이 실행됩니다.

        
  아래 사진은 구현한 기능입니다.
        
        
![1 1](https://user-images.githubusercontent.com/105340085/215736858-879fc8f8-5b3d-499a-bfd0-b922eba394f6.png)
        
        
  기본적으로 layout은 constraintlayout 을 사용했습니다.
        
![2 1](https://user-images.githubusercontent.com/105340085/215736862-966fc0dc-2c96-4940-aec3-582a6178a371.png)
        
        
  글을 클릭했을때 설명과 사진 그리고 리뷰를 적을수 있는 공간입니다.
        
![3](https://user-images.githubusercontent.com/105340085/215736850-849890c2-8ae9-4436-8675-5fb0aa6a3e8a.PNG)
        
        
  저장하기 버튼을 사용해 리뷰를 저장할수 있습니다.
        
![4 1](https://user-images.githubusercontent.com/105340085/215736855-d753aaf6-ffa8-4ae0-8221-63f3ade7da77.png)
        
        
  SupportSQLiteDatabase 을 이용한 검색기능입니다.
        
![5](https://user-images.githubusercontent.com/105340085/215736857-d38da740-b1ad-44f0-9074-ba5527245813.PNG)

