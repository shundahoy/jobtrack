# jobtrack

職務経歴書の登録・管理を行う Spring Boot アプリケーションです。

## 必要な環境

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)（Docker Compose 含む）

## 起動方法

1. Docker Desktop を起動する
2. プロジェクトルートで以下を実行する

```bash
docker compose up --build
```

バックグラウンドで起動する場合:

```bash
docker compose up --build -d
```

初回はイメージのビルドと PostgreSQL の初期化に数分かかることがあります。

## アクセス

| 用途                         | URL                                   |
| ---------------------------- | ------------------------------------- |
| 職務経歴書フォーム（トップ） | http://localhost:8080/                |
| ヘルスチェック API           | http://localhost:8080/api/health      |
| Actuator                     | http://localhost:8080/actuator/health |

## 停止方法

```bash
docker compose down
```

DB のデータも削除する場合:

```bash
docker compose down -v
```

## 構成

| サービス     | ポート | 説明               |
| ------------ | ------ | ------------------ |
| jobtrack-app | 8080   | Spring Boot アプリ |
| jobtrack-db  | 5432   | PostgreSQL 16      |

DB 接続情報（ローカル開発用）:

- ホスト: `localhost`
- ポート: `5432`
- データベース: `jobtrack`
- ユーザー / パスワード: `jobtrack` / `jobtrack`

## 技術スタック

| 区分           | 技術                          |
| -------------- | ----------------------------- |
| 言語           | Java 21                       |
| フレームワーク | Spring Boot 3.4               |
| テンプレート   | Thymeleaf                     |
| 永続化         | Spring Data JPA / PostgreSQL 16 |
| コンテナ       | Docker / Docker Compose       |

## アーキテクチャ

本プロジェクトは **ドメイン駆動設計（DDD）** の考え方に基づき、**オニオンアーキテクチャ** を採用しています。

### 設計方針

- **ドメインを中心に据える** — ビジネスルールや不変条件はドメイン層に集約し、フレームワークや DB の詳細に依存させない
- **依存の方向を内側へ** — 外側の層（Presentation / Infrastructure）は内側の層（Application / Domain）に依存する。Domain 層は他の層に依存しない
- **集約で整合性を保つ** — 職務経歴書の各エンティティを集約単位でモデリングし、トランザクション境界を明確にする

参考: [オニオンアーキテクチャとは](https://zenn.dev/rai0/articles/5982d9223ddfa0)

### レイヤー構成

```
┌─────────────────────────────────────────────┐
│  Presentation（プレゼンテーション層）         │  ← HTTP リクエスト / Thymeleaf 画面
├─────────────────────────────────────────────┤
│  Application（アプリケーション層）            │  ← ユースケースの調整・トランザクション
├─────────────────────────────────────────────┤
│  Domain（ドメイン層）                         │  ← エンティティ・値オブジェクト・ドメインサービス
├─────────────────────────────────────────────┤
│  Infrastructure（インフラストラクチャ層）     │  ← JPA 実装・外部 API 連携
└─────────────────────────────────────────────┘
         ↑ 依存の方向は常に内側（Domain）へ
```

| レイヤー         | パッケージ（予定）              | 責務 |
| ---------------- | ------------------------------- | ---- |
| Presentation     | `com.jobtrack.presentation`     | コントローラ、リクエスト/レスポンスの変換、画面表示 |
| Application      | `com.jobtrack.application`      | ユースケース（サービス）、DTO、リポジトリのインターフェース定義 |
| Domain           | `com.jobtrack.domain`           | 集約ルート、エンティティ、値オブジェクト、ドメインイベント |
| Infrastructure   | `com.jobtrack.infrastructure`   | JPA エンティティ、リポジトリ実装、DB マッピング |

### ドメインモデル（集約）

職務経歴書を構成する主要な集約は以下のとおりです。詳細は `doc/domain_model.png` を参照してください。

| 集約             | 概要 |
| ---------------- | ---- |
| **User**         | ユーザー基本情報（氏名・連絡先・希望条件・自己 PR など）。スキル・資格・学歴・職歴への参照を保持する集約ルート |
| **Career**       | 職歴（会社名・部署・役職・在籍期間など）。配下に **Project**（参画プロジェクト）を持つ |
| **Skill**        | ユーザーのスキル（テクノロジーへの参照・習熟度・経験年数） |
| **Certification**| 保有資格 |
| **Education**    | 学歴 |
| **Technology**   | 技術マスタ（Java, Spring Boot など）。Skill や Project から参照される |

集約間の参照は ID で行い、他集約のオブジェクトを直接保持しないことで、境界を明確にしています。

### ディレクトリ構成

```
jobtrack/
├── doc/
│   ├── domain_model.drawio   # ドメインモデル図（draw.io）
│   ├── domain_model.png      # ドメインモデル図（エクスポート）
│   ├── er.dbml               # ER 図定義（dbdiagram 用）
│   └── er.dbdiagram          # ER 図レイアウト
├── src/main/java/com/jobtrack/
│   ├── JobtrackApplication.java
│   ├── presentation/         # プレゼンテーション層（実装済み）
│   │   └── controller/
│   ├── application/          # アプリケーション層（今後追加）
│   ├── domain/               # ドメイン層（今後追加）
│   └── infrastructure/       # インフラストラクチャ層（今後追加）
├── src/main/resources/
│   ├── templates/            # Thymeleaf テンプレート
│   └── static/               # CSS / JavaScript
├── docker-compose.yml
└── Dockerfile
```

### 実装状況

| レイヤー       | 状態 | 内容 |
| -------------- | ---- | ---- |
| Presentation   | 一部実装済み | 職務経歴書フォーム画面（基本情報・職歴・スキル・学歴・資格・自己 PR）、ヘルスチェック API |
| Application    | 未実装 | ユースケース・リポジトリ IF は今後追加予定 |
| Domain         | 未実装 | 集約・エンティティは今後追加予定 |
| Infrastructure | 未実装 | JPA リポジトリ・DB マイグレーションは今後追加予定 |

現時点では UI（Presentation 層）のプロトタイプが先行しており、ドメイン層以降は段階的に実装していく方針です。

## ドキュメント

| ファイル                  | 説明 |
| ------------------------- | ---- |
| `doc/domain_model.png`    | DDD に基づく集約・エンティティの関係図 |
| `doc/er.dbml`             | PostgreSQL 向け ER 図（[dbdiagram.io](https://dbdiagram.io) で閲覧可能） |
