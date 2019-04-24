## GroupAdapter Characteristic
**项目出发点**：由于一个RecyclerView加载了多个接口数据，为了提高显示效率：
    <li> 多个接口数据全部返回在显示(不可取)
    <li> 每个接口数据返回重新排序(无法复用，逻辑处理麻烦,不可取)
    <li> 在adapter内部处理，外部传入组号即可随时随地的增加、删除、替换并排序数据(当前方案)

RecyclerView分组Adapter，数据异步或同步加入adapter后，**按照加入组号排列数据，解放异步接口数据排序问题**

### PREVIEW
![](/preview/001.png) ![](/preview/002.png) ![](/preview/003.png)

### USE by Kotlin
    implementation 'com.uis:groupadapter:0.4.0
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation "com.android.support:recyclerview-v7:$supportVer"

``` 项目中使用的是compileOnly,使用者需自行加入外部依赖库 ```

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
fun initGroup(groupSize :Int)//初始化(重置)分组个数（默认为1组）

fun addEntity(group :Int,entity :GroupEntity)//加入数据到第group个分组，group范围[0,groupSize-1]

fun removeEntity(group :Int)//移除分组数据

fun clearAllEntity()//移除所有数据

fun removePositonEntity(positon: Int)//移出指定位置（RecyclerView中位置）

fun getPositon(group: Int)//获取该组开始position

fun getSize(group: Int)//获取该组大小

fun changeEntity(group :Int,entities :MutableList<GroupEntity>)//替换组号下列表，无论该组下是否有数据

fun changeEntity(group: Int,entity: GroupEntity)//新组号下第0个位置数据，此组无数据不更新

fun changePositionEntity(position: Int,entity: GroupEntity)//更新全局position位置
```


### VERSION

Version|Descipt|Fixed|Time
----|----|----|----
0.1.0|初始版本| |2018/11/24
0.2.0|增加删除函数| |2018/11/27
0.3.0|默认初始化| |2018/11/28
0.4.0|增加更新函数| fixed removePositonEntity|2019/4/24

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