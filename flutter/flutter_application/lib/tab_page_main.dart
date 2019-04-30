import 'package:flutter/material.dart';
import 'package:flutter_application/character.dart';
import 'package:flutter_application/scroll_list_character.dart';

class DnDTabbedPage extends StatefulWidget {
  const DnDTabbedPage({Key key}) : super(key: key);

  @override
  _DnDTabbedPageState createState() => _DnDTabbedPageState();
}


class _DnDTabbedPageState extends State<DnDTabbedPage> with SingleTickerProviderStateMixin {
  final List<Tab> _tabs = <Tab>[
  Tab(text: "Char"),
  Tab(text: "Comb",)
  ];

  TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(vsync: this, length: _tabs.length);
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("dnd"),
        bottom: TabBar(
          controller: _tabController,
          tabs: _tabs,
        ),
      ),
      body: TabBarView(
        controller: _tabController,
        children: _tabs.map((Tab tab) {
          return Center(child: CharacterScrollList());
        }).toList(),
      ),
      );
  }

}