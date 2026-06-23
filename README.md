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

## アーキテクチャ

オニオンアーキテクチャ
参照：https://zenn.dev/rai0/articles/5982d9223ddfa0
