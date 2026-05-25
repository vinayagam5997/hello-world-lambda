# hello-world-lambda

A minimal **Java 17 AWS Lambda** function exposed via **API Gateway**, built with Maven and deployed using **AWS SAM**.

---

## Project Structure

```
hello-world-lambda
├── src/main/java/com/vinay
│   └── Handler.java          # Lambda request handler
├── pom.xml                   # Maven build & dependencies
├── buildspec.yml             # AWS CodeBuild pipeline spec
├── template.yaml             # SAM / CloudFormation template
└── README.md
```

---

## Prerequisites

| Tool | Version |
|------|---------|
| Java | 17 (Amazon Corretto recommended) |
| Maven | 3.9+ |
| AWS SAM CLI | latest |
| AWS CLI | v2 |

---

## Local Development

### Build
```bash
mvn clean package
```
Produces `target/hello-world-lambda-1.0.0.jar` (fat JAR via Maven Shade).

### Run Tests
```bash
mvn test
```

### Local Invoke (SAM)
```bash
sam local invoke HelloWorldFunction \
  --event events/apigw-get.json
```

### Local API
```bash
sam local start-api
# GET http://localhost:3000/hello?name=Vinay
```

---

## Deployment

### One-time S3 bucket setup
```bash
aws s3 mb s3://<your-deployment-bucket>
```

### Package & Deploy (dev)
```bash
sam deploy \
  --template-file template.yaml \
  --stack-name hello-world-dev \
  --s3-bucket <your-deployment-bucket> \
  --parameter-overrides Env=dev \
  --capabilities CAPABILITY_IAM CAPABILITY_NAMED_IAM \
  --region ap-south-1
```

### Environments
| Env | Stack name |
|-----|-----------|
| `dev` | `hello-world-dev` |
| `stage` | `hello-world-stage` |
| `prd` | `hello-world-prd` |

---

## API

### Endpoint
```
GET /hello?name={name}
```

### Response
```json
{ "message": "Hello, Vinay!" }
```
Returns `"Hello, World!"` when the `name` query param is omitted.

---

## CI/CD (CodeBuild)

`buildspec.yml` drives the pipeline:

1. **install** — sets up Amazon Corretto 17
2. **pre_build** — runs `mvn test`
3. **build** — runs `mvn package`, then `aws cloudformation package`
4. **post_build** — emits `packaged-template.yaml` as the deploy artifact

Set the `S3_BUCKET` environment variable in your CodeBuild project to match your deployment bucket.

---

## License
MIT
