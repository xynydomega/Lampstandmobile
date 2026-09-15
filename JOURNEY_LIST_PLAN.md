# Journey List Screen Plan — 1:1 Replica of Web `app/my-journey/page.tsx`

> Track this file to follow build. Checkboxes update as we go.

## Goal
Replicate web LIST screen as first `My Journey` tab screen. Web flow: `BottomNav JOURNEY` -> LIST -> click PathCard -> DETAIL (`[id]/page.tsx`) -> SESSION. Mobile currently goes `JOURNEY` -> DETAIL mock directly. This fixes navigation and establishes SoC pattern like `ui/dashboard/` (State/Action/DataFlow/ViewModel/Screen).

## Web Reference (7 route screens, 12 with session steps)
1. `app/my-journey/page.tsx` **LIST** — `My Journey` heading, `PathCard` list `gap 12px`, empty `No path selected -> Go to Paths`
2. `app/my-journey/[id]/page.tsx` **DETAIL** — Hero, Continue, Schedule, Sessions (currently mobile `JourneyScreen.kt:52` mock)
3. `app/my-journey/[id]/session/page.tsx` **SESSION** + 5 sections
4. `app/my-journey/[id]/feedback/page.tsx` FEEDBACK
5. `app/my-journey/[id]/completed/page.tsx` COMPLETED
6. `app/my-journey/[id]/looking-forward/page.tsx` LOOKING_FORWARD
7. `app/my-journey/[id]/support/page.tsx` SUPPORT

We build **#1 LIST** now.

## Separation of Concerns (SoC) — File Map
Follow `dashboard/DashboardState.kt:4`, `DashboardAction.kt:3`, `DashboardDataFlow.kt:14`, `DashboardViewModel.kt:42` pattern.

### New Files (location: `ui/journey/list/`)
- [x] `ui/journey/list/MyJourneyListState.kt`
  - `data class MyJourneyListState(isLoading:Boolean=false, paths:List<JourneyPathUi>=emptyList(), errorMessage:String?=null)`
  - `data class JourneyPathUi(id:String, title:String, description:String, durationDays:Int, thumbnailRes:Int|thumbnailUrl:String?)`
  - Mirrors `web mockData.ts JourneyPath` + `paths:getHomeState` shape.

- [x] `ui/journey/list/MyJourneyListAction.kt`
  - `sealed interface MyJourneyListAction { data object Refresh; data class PathClicked(val id:String); data object GoToPaths }`

- [x] `ui/journey/list/MyJourneyListDataFlow.kt`
  - `class MyJourneyListDataFlow { fun getMyJourneyPaths():Flow<Result<List<Map<String,Any?>>>> = subscribe("myJourney:get" or "paths:getMyJourney") }`
  - Pattern: `withContext(Dispatchers.IO) runCatching { ConvexClientProvider.client.subscribe(...) }` like `PathsDataFlow.kt:5`.
  - Temp fallback: emit `mockPaths` (Trust in Uncertainty) until Convex function wired.

- [x] `ui/journey/list/MyJourneyListViewModel.kt`
  - `class MyJourneyListViewModel(private val dataFlow:MyJourneyListDataFlow=...):ViewModel() { private val _uiState=MutableStateFlow(MyJourneyListState(isLoading=true)); val uiState:StateFlow; fun onAction(action); private fun refreshData() }`
  - Like `DashboardViewModel.kt:42`.

- [x] `ui/journey/list/MyJourneyListScreen.kt`
  - `MyJourneyListScreen(viewModel, onNavigate, contentPadding)`
  - `MyJourneyListContent(state, onAction, onNavigate, contentPadding)` — 1:1 replica:
    - `Box fillMaxSize bg #FDFDFD padding(contentPadding)`
    - `Column verticalScroll padding bottom 16dp`
    - Header `Text My Journey 20sp SemiBold #184159 padding 24dp top 48dp bottom 16dp` (web heading)
    - If `paths.isEmpty() && !isLoading` -> EmptyState `Text No path selected + Sorry... + Button Go to Paths 275x42 bg #335E78 -> AppDestination.PATHS` (web EmptyState)
    - Else `Column padding 24dp spacedBy 12dp` -> `PathCard` each `Row fillMaxWidth clip 8dp bg White border 1dp #E8E8E8 padding 14dp`:
      - Left: `Column` -> `Row CalendarToday 14dp #4A6B82 + Text 7 days Bold 11sp` -> `Title 15sp Bold #1E293B` -> `Description 11sp #64748B`
      - Right: `Box 85x85 clip 4dp bg #1E1E1E Image sailboat Crop`
      - Clickable `onAction(PathClicked)` or `onNavigate(JOURNEY_DETAIL)`
  - Previews: `MyJourneyListPreview` with paths + empty.

### Modified Files
- [x] `navigation/AppNavigationState.kt:5` — add `JOURNEY_LIST` and `JOURNEY_DETAIL` or use sealed `AppDestination.JOURNEY_LIST` / `data class JOURNEY_DETAIL(val id:String)`. Simplest: add `JOURNEY_LIST`, keep `JOURNEY` as alias for list, add `JOURNEY_DETAIL`. Map: `PATH_DETAIL` already exists.
- [x] `navigation/AppNavigation.kt:86` — map `bottomNavTab JOURNEY -> JOURNEY_LIST + JOURNEY_DETAIL`, `Scaffold bottomBar`, `when` branch `JOURNEY_LIST -> MyJourneyListScreen`, `JOURNEY_DETAIL -> JourneyDetailScreen(old JourneyScreen)`.
- [ ] `ui/journey/JourneyScreen.kt:52` — rename to `JourneyDetailScreen` or keep as detail; fix back click `onBack` wired (currently not clickable `JourneyScreen.kt:93` Box).

## Build Order
1. `MyJourneyListState.kt` + `MyJourneyListAction.kt` (types)
2. `MyJourneyListDataFlow.kt` (data)
3. `MyJourneyListViewModel.kt` (logic)
4. `MyJourneyListScreen.kt` (UI 1:1)
5. `AppNavigationState.kt` + `AppNavigation.kt` (wiring + BottomNav tab)
6. `./gradlew :app:assembleDebug` verify + previews `AppNavigationJourneyPreview` shows list.

## Verification
- `grep -r MyJourneyList app/src/main/java` -> 5 files.
- Previews: `MyJourneyListPreview` empty + with path.
- `assembleDebug BUILD SUCCESSFUL`.
- Visual diff vs web `app/my-journey/page.tsx` at 390px width: heading, card `85x85`, `Go to Paths` CTA.

## Next Steps After This File (not in this plan)
- Phase 2: Make `JourneyDetailScreen` data-driven (replace hardcoded sessions `JourneyScreen.kt:58`).
- Phase 3: `MyJourneySessionScreen` 5-step snap host (`session/page.tsx`).

## How to Track
Check boxes above. File location: `LampStandMobile/JOURNEY_LIST_PLAN.md`.
Ask me "proceed" to start building files in order.
