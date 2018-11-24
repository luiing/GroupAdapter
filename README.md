## Welcome to GroupAdapter
**项目出发点**：由于一个RecyclerView加载了多个接口数据，为了提高显示效率：
    <li> 多个接口数据全部返回在显示并不可取
    <li> 每个接口数据返回重新排序，无法复用，逻辑处理麻烦
    <li> 在adapter内部处理，外部传入组号即可随时随地的刷新并排序数据

RecyclerView分组Adapter，数据异步或同步加入adapter后，**按照加入组号排列数据，解放异步接口数据排序问题**

### PREVIEW
![/preview/001.png] ![/preview/002.png] ![/preview/003.png]

### USE by Kotlin
    implementation 'com.uis:groupadapter:0.1.0
    implementation "com.android.support:recyclerview-v7:$supportVer"

```
    //ViewHolder创建就是这么简单
    class TxtLargeVH(parent:ViewGroup) :GroupHolder<String>(R.layout.ui_item_txt_large,parent){
        override fun bindVH(item: String) {
            itemView.tv_txt_large.text = item
        }
    }
    //Adapter创建也是如此简单
    class DemoAdapter :GroupAdapter(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder<out Any>{
            return when(viewType){
                VT_TXT_LARGE -> TxtLargeVH(parent)
                VT_TXT_BLUE  -> TxtBlueVH(parent)
                else         -> TxtVH(parent)
            }
        }
    }
    //数据装载就是如此任性
    adapter.initGroup(3)//初始化分组，分组从0-2
    adapter.addEntity(1,GroupEntity(VT_TXT_LARGE,"large"))
```


### 函数介绍


```
fun initGroup(groupSize :Int)//初始化分组个数
fun addEntity(group :Int,entity :GroupEntity)//加入数据到第group个分组，group范围[0,groupSize-1]
fun removeEntity(group :Int)//移除分组数据
fun clearAllEntity()//移除所有数据
```


### VERSION

Version|Descipt|Fixed|Time
----|----|----|----
0.1.0|初始版本| |2018/11/24

### LICENSE
MIT License

Copyright (c) 2018 luiing

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.